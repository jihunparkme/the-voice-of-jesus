package com.jesus.voice.youtube.extractor

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test

class PlayListExtractorTest {
    @Test
    fun parseJson() {
        val playListId = "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9"
        val resource = javaClass.classLoader.getResource("html/play-list.html")
        val playListHtml = Files.readString(Paths.get(resource!!.toURI()))
        val respnse = PlayListExtractor.extractPlayList(playListId, playListHtml)
        println(respnse)
    }
}