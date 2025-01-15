package com.jesus.voice.test

import com.jesus.voice.common.IntegrationTest
import com.jesus.voice.youtube.client.YoutubeClient
import kotlin.test.Test

@IntegrationTest
class Test(
    private val youtubeClient: YoutubeClient,
) {
    @Test
    fun aa() {
        val playListId = "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9"
        val response = youtubeClient.getPlayList(playListId).getOrDefault("")
        val splitHtml = response.split("\"twoColumnBrowseResultsRenderer\":")
        val playlistDetail = splitHtml[1].split(".\"frameworkUpdates\"")
        println(playlistDetail[0].replace("\n", ""))
        // TODO: Json parsing
    }
}