package com.jesus.voice.youtube.client

import com.jesus.voice.common.util.logger
import com.jesus.voice.config.KtorClient
import com.jesus.voice.youtube.dto.Const.YOUTUBE_WATCH_URL
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import org.springframework.stereotype.Component

@Component
class YoutubeClient(
    private val ktorClient: KtorClient,
) {
    suspend fun getVideoPage(videoId: String): String {
        return runCatching {
            val response = ktorClient.get(YOUTUBE_WATCH_URL + videoId)
            handleResponse(response, videoId)
        }.onFailure {
            logger.error("video page request exception. videoId: $videoId, error: ${it.message}", it)
        }.getOrElse { "" }
    }

    private suspend fun handleResponse(response: HttpResponse, videoId: String): String {
        return if (response.status.isSuccess()) {
            response.body() // body는 suspend 함수이므로 호출 가능
        } else {
            logger.error("Video page request failed. videoId: $videoId, status: ${response.status.value}")
            ""
        }
    }
}