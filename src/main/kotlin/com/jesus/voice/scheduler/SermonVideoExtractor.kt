package com.jesus.voice.scheduler

import com.jesus.voice.common.util.logger
import com.jesus.voice.domain.sermon.Sermon
import com.jesus.voice.domain.sermon.SermonRepository
import com.jesus.voice.youtube.dto.PlayListVideo
import com.jesus.voice.youtube.service.YoutubeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SermonVideoExtractor(
    private val youtubeService: YoutubeService,
    private val sermonRepository: SermonRepository,
) {
    @Scheduled(cron = "0 0 1 * * ?")
    fun runScheduler() {
        PLAY_LIST_INVENTORY.forEach { saveSermonVideo(it) }
    }

    private fun saveSermonVideo(playListId: String) {
        val playListVideos = youtubeService.getVideoIdFromPlayList(playListId)
        val recentVideos = playListVideos.take(5)
            .filter { sermonRepository.existsByVideoIdNot(it.videoId) }
        recentVideos.forEach {
            val sermon = generateSermon(it)
            sermonRepository.save(sermon)
        }
    }

    private fun generateSermon(video: PlayListVideo): Sermon {
        // TODO: 자막 URL 추출
        // TODO: 자막 추출
        // TODO: 자막 형태소 분석으로 자막 포맷 정리 및 키워드 추출
        // TODO: 자막 요약
        // TODO: 몽고 저장
        return Sermon()
    }

    companion object {
        private val log by logger()

        val PLAY_LIST_INVENTORY = listOf(
            "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9&index=1", // 주1부예배
            "LVK2VzE62knxZpbQVCg_4VtY-FA5X80Jl&index=14", // 다드림예배
        )
    }
}
