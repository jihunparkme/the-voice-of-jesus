package com.jesus.voice.scheduler

import com.jesus.voice.common.dtos.AYMCPlayList
import com.jesus.voice.common.util.logger
import com.jesus.voice.aggregate.sermon.domain.Sermon
import com.jesus.voice.aggregate.sermon.domain.SermonRepository
import com.jesus.voice.external.komoran.MorphemeAnalyzer
import com.jesus.voice.external.openai.gemini.service.GeminiChatService
import com.jesus.voice.external.youtube.dto.PlayListVideo
import com.jesus.voice.external.youtube.dto.VideoId
import com.jesus.voice.external.youtube.service.YoutubeService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SermonVideoExtractor(
    private val youtubeService: YoutubeService,
    private val sermonRepository: SermonRepository,
    private val geminiChatService: GeminiChatService,
) {
    @Scheduled(cron = "0 0 20 * * ?")
    fun runScheduler() = listOf(
        AYMCPlayList.SUNDAY_1,
        AYMCPlayList.DADRIM,
        AYMCPlayList.DAWN,
    ).forEach { channel ->
        runBlocking {
            launch {
                saveSermonVideo(channel)
                sleepRandomDuration()
            }
        }
    }

    private fun saveSermonVideo(channel: AYMCPlayList) =
        youtubeService.getVideoIdFromPlayList(channel.id)
            .take(1)
            .filterNot { sermonRepository.existsByVideoId(it.videoId) }
            .map { generateSermon(it, channel) }
            .forEach { sermon ->
                sermonRepository.save(sermon)
                log.info("✅✅✅ Sermon saved: $sermon")
            }

    private fun generateSermon(playListVideo: PlayListVideo, channel: AYMCPlayList): Sermon {
        val transcript = runCatching {
            youtubeService.getTranscript(VideoId(playListVideo.videoId))
        }.getOrDefault("")
        if (transcript.isBlank()) {
            return playListVideo.toSermon(
                playList = channel.toDocument()
            )
        }
        val (refinedContent, wordCount) = MorphemeAnalyzer.analyze(transcript)
        val summarizedContent = geminiChatService.chat(CHAT_PREFIX + refinedContent)
            .replace("  ", " ")
        return playListVideo.toSermon(
            playList = channel.toDocument(),
            transcript = refinedContent,
            summary = summarizedContent,
            wordCount = wordCount,
        )
    }

    private suspend fun sleepRandomDuration() {
        val randomDuration = (10000L..20000L).random()
        log.info("🌙🌙🌙 Sleeping for $randomDuration ms...")
        delay(randomDuration)
    }

    companion object {
        private val log by logger()
        private val CHAT_PREFIX = "아래 글은 교회의 설교 내용입니다. 내용을 요약해 주세요.\n\n"
    }
}

