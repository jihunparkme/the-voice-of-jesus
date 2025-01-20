package com.jesus.voice.youtube.client

import com.jesus.voice.common.exception.YoutubeClientException
import com.jesus.voice.config.KtorClient
import com.jesus.voice.youtube.dto.Const.YOUTUBE_PLAYLIST_URL
import com.jesus.voice.youtube.dto.Const.YOUTUBE_WATCH_URL
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class YoutubeClient(
    private val ktorClient: KtorClient,
) {
    @Throws(YoutubeClientException::class)
    fun getVideoPage(videoId: String): Result<String> =
        runCatching {
            runBlocking {
                val response = ktorClient.get(YOUTUBE_WATCH_URL + videoId)
                ktorClient.handleResponse(response, videoId)
            }
        }.onFailure {
            throw YoutubeClientException("videoId", videoId, it)
        }

    @Throws(YoutubeClientException::class)
    fun getTranscript(videoId: String, transcriptUrl: String): Result<String> =
        runCatching {
            runBlocking {
                val response = ktorClient.get(transcriptUrl)
                ktorClient.handleResponse(response, videoId)
            }
        }.onFailure {
            throw YoutubeClientException("videoId", videoId, it)
        }

    @Throws(YoutubeClientException::class)
    fun getPlayList(playListId: String): Result<String> =
        runCatching {
            runBlocking {
                val response = ktorClient.get(YOUTUBE_PLAYLIST_URL + playListId)
                ktorClient.handleResponse(response)
            }
        }.onFailure {
            throw YoutubeClientException("playListId", playListId, it)
        }
}