package com.jesus.voice.scheduler

import com.jesus.voice.common.util.logger
import com.jesus.voice.domain.sermon.Sermon
import com.jesus.voice.domain.sermon.SermonRepository
import com.jesus.voice.komoran.MorphemeAnalyzer
import com.jesus.voice.openai.gemini.service.GeminiChatService
import com.jesus.voice.youtube.dto.PlayListVideo
import com.jesus.voice.youtube.dto.VideoId
import com.jesus.voice.youtube.service.YoutubeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SermonVideoExtractor(
    private val youtubeService: YoutubeService,
    private val sermonRepository: SermonRepository,
    private val geminiChatService: GeminiChatService,
) {
    @Scheduled(cron = "0 0 1 * * ?")
    fun runScheduler() {
        PLAY_LIST_INVENTORY.forEach { saveSermonVideo(it) }
        // TODO: 재생목록 이름 channel
    }

    private fun saveSermonVideo(playListId: String) {
        val playListVideos = youtubeService.getVideoIdFromPlayList(playListId)
        val recentVideos = playListVideos.take(1)
            .filter {
                !sermonRepository.existsByVideoId(it.videoId)
            }
        recentVideos.forEach {
            val sermon = generateSermon(it)
            sermonRepository.save(sermon)
            log.info("✅saved sermon. $sermon")
        }
    }

    private fun generateSermon(playListVideo: PlayListVideo): Sermon {
        val transcript = runCatching {
            youtubeService.getTranscript(VideoId(playListVideo.videoId))
        }.getOrDefault("")
        if (transcript.isEmpty()) {
            return Sermon(
                videoId = playListVideo.videoId,
                thumbnailUrl = playListVideo.thumbnailUrl,
                title = playListVideo.title,
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
        private val PLAY_LIST_INVENTORY = listOf(
            "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9", // 주1부예배
            "PLVK2VzE62knxZpbQVCg_4VtY-FA5X80Jl", // 다드림예배
            "PLVK2VzE62knwgDZIzr-bxi_S6URleOU8C", // 새벽기도회
            // 금요회복기도회
            // 안양감리교회 주일예배
        )
    }
}
