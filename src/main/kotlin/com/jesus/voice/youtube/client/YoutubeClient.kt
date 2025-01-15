package com.jesus.voice.youtube.client

import com.jesus.voice.common.util.logger
import com.jesus.voice.config.KtorClient
import com.jesus.voice.youtube.dto.Const.YOUTUBE_WATCH_URL
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class YoutubeClient(
    private val ktorClient: KtorClient,
) {
    private val log by logger()

    fun getVideoPage(videoId: String): String =
        runCatching {
            runBlocking {
                val response = ktorClient.get(YOUTUBE_WATCH_URL + videoId)
                handleResponse(response, videoId)
            }
        }.onFailure {
            log.error("video page request exception. videoId: $videoId, error: ${it.message}", it)
        }.getOrElse { "" }

    fun getTranscript(videoId: String, transcriptUrl: String): String =
        runCatching {
            runBlocking {
                val response = ktorClient.get(transcriptUrl)
                handleResponse(response, videoId)
            }
        }.onFailure {
            log.error("video page request exception. videoId: $videoId, error: ${it.message}", it)
        }.getOrElse { "" }

    private suspend fun handleResponse(response: HttpResponse, videoId: String): String =
        if (response.status.isSuccess()) {
            response.body()
        } else {
            log.error("Video page request failed. videoId: $videoId, status: ${response.status.value}")
            ""
        }
}