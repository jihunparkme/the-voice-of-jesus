package com.jesus.voice.youtube.client

import com.jesus.voice.config.KtorClient
import com.jesus.voice.config.ResponseResult
import com.jesus.voice.config.responseResult
import com.jesus.voice.youtube.dto.Const.YOUTUBE_PLAYLIST_URL
import com.jesus.voice.youtube.dto.Const.YOUTUBE_WATCH_URL
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class YoutubeClient(
    private val ktorClient: KtorClient,
) {
    fun getVideoPage(videoId: String): ResponseResult<String> =
        runBlocking {
            ktorClient.get(YOUTUBE_WATCH_URL + videoId)
                .responseResult<String>()
        }

    fun getTranscript(videoId: String, transcriptUrl: String): ResponseResult<String> =
        runBlocking {
            ktorClient.get(transcriptUrl)
                .responseResult<String>()
        }

    fun getPlayList(playListId: String): ResponseResult<String> =
        runBlocking {
            ktorClient.get(YOUTUBE_PLAYLIST_URL + playListId)
                .responseResult<String>()
        }
}