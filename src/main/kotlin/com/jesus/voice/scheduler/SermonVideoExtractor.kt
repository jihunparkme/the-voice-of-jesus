package com.jesus.voice.scheduler

import com.jesus.voice.common.dtos.PlayListChannel
import com.jesus.voice.common.util.logger
import com.jesus.voice.domain.sermon.Sermon
import com.jesus.voice.domain.sermon.SermonRepository
import com.jesus.voice.komoran.MorphemeAnalyzer
import com.jesus.voice.openai.gemini.service.GeminiChatService
import com.jesus.voice.youtube.dto.PlayListVideo
import com.jesus.voice.youtube.dto.VideoId
import com.jesus.voice.youtube.service.YoutubeService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.lang.Thread.sleep

@Component
class SermonVideoExtractor(
    private val youtubeService: YoutubeService,
    private val sermonRepository: SermonRepository,
    private val geminiChatService: GeminiChatService,
) {
    @Scheduled(cron = "0 0 1 * * ?")
    fun runScheduler() {
        listOf(
            PlayListChannel.SUNDAY_1,
            PlayListChannel.DADRIM,
            PlayListChannel.DAWN,
        ).forEach {
            runBlocking {
                launch {
                    saveSermonVideo(it)
                    val random = (10000L..20000L).random()
                    log.info("\uD83C\uDF1D\uD83C\uDF1D\uD83C\uDF1D sleep a moment... $random ms.")
                    sleep(random)
                }
            }
        }
    }

    private fun saveSermonVideo(channel: PlayListChannel) {
        val playListVideos = youtubeService.getVideoIdFromPlayList(channel.id)
        val recentVideos = playListVideos.take(1)
            .filter {
                !sermonRepository.existsByVideoId(it.videoId)
            }
        recentVideos.forEach {
            val sermon = generateSermon(it, channel)
            sermonRepository.save(sermon)
            log.info("✅✅✅ saved sermon. $sermon")
        }
    }

    private fun generateSermon(playListVideo: PlayListVideo, channel: PlayListChannel): Sermon {
        val transcript = runCatching {
            youtubeService.getTranscript(VideoId(playListVideo.videoId))
        }.getOrDefault("")
        // TODO: refactor
        if (transcript.isEmpty()) {
            return Sermon(
                videoId = playListVideo.videoId,
                thumbnailUrl = playListVideo.thumbnailUrl,
                title = playListVideo.title,
                channel = channel.toDocument(),
                publisher = playListVideo.publisher,
                streamingTime = playListVideo.streamingTime,
                uploadedDate = playListVideo.uploadedDate,
                beforeDate = playListVideo.beforeDate,
            )
        }
        val (refinedContent, wordCount) = MorphemeAnalyzer.analyze(transcript)
        val summarizedContent = geminiChatService.chat(CHAT_PREFIX + refinedContent)
            .replace("  ", " ")
        return Sermon(
            videoId = playListVideo.videoId,
            thumbnailUrl = playListVideo.thumbnailUrl,
            title = playListVideo.title,
            channel = channel.toDocument(),
            publisher = playListVideo.publisher,
            streamingTime = playListVideo.streamingTime,
            uploadedDate = playListVideo.uploadedDate,
            beforeDate = playListVideo.beforeDate,
            transcript = refinedContent,
            summary = summarizedContent,
            wordCount = wordCount,
        )
    }

    companion object {
        private val log by logger()

        private val CHAT_PREFIX = "아래 글은 교회의 설교 내용입니다. 내용을 요약해 주세요.\n\n"
    }
}

