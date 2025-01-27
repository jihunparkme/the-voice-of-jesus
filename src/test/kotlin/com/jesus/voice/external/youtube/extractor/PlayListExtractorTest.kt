package com.jesus.voice.external.youtube.extractor

import com.jesus.voice.external.youtube.extractor.PlayListExtractor
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test

class PlayListExtractorTest {
    @Test
    fun success_extract_playList() {
        val playListId = "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9"
        val resource = javaClass.classLoader.getResource("html/play-list.txt")
        val playListHtml = Files.readString(Paths.get(resource!!.toURI()))

        val result = PlayListExtractor.extractPlayList(playListId, playListHtml)

        result.size shouldBe 54

        val first = result.first()
        first.videoId shouldBe "T1MTC6_KdNk"
        first.thumbnailUrl shouldBe "https://i.ytimg.com/vi/T1MTC6_KdNk/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLDpiIAqBASANHlIuDW2B1I6jspvlA"
        first.title shouldBe "[안양감리교회 주일1부예배] \"하나님이 보시는 헌신\" (눅 21:1-6)_안양감리교회 김수겸 목사_2025.1.12"
        first.publisher shouldBe "안양감리교회"
        first.streamingTime shouldBe "34:25"
        first.uploadedDate shouldBe "2025.1.12"
        first.beforeDate shouldBe "4일 전"
    }
}