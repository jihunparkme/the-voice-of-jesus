package com.jesus.voice.external.youtube.extractor

import com.jesus.voice.external.youtube.extractor.PlayListExtractor.refineTitle
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

    @Test
    fun refine_title() {
        var title = "[안양감리교회 주일1부예배] \"두려움을 이기는 힘, 절제\" (딤후 1:7-8)_안양감리교회 천주람 목사_2025.2.9"
        title.refineTitle() shouldBe "\"두려움을 이기는 힘, 절제\" (딤후 1:7-8) 천주람 목사_2025.2.9"

        title = "[다드림예배] \"한계를 인정해야 한계를 넘어선다\"(전 1:12-18)_안양감리교회 김병재 목사_2025.02.09"
        title.refineTitle() shouldBe "\"한계를 인정해야 한계를 넘어선다\"(전 1:12-18) 김병재 목사_2025.02.09"

        title = "[안양감리교회 새벽기도회] 2025.2.11 화"
        title.refineTitle() shouldBe "2025.2.11 화"

        title = "[금요회복기도회] \"곧은 길을 만들라\"(히 12:11-13)_안양감리교회 김상헌 목사_2025.2.7"
        title.refineTitle() shouldBe "\"곧은 길을 만들라\"(히 12:11-13) 김상헌 목사_2025.2.7"

        title = "[안양감리교회 주일예배] \"주기도문4 하나님의 나라가 임하소서\" (마 6:9-10)_안양감리교회 임용택 담임목사_2025.2.9"
        title.refineTitle() shouldBe "\"주기도문4 하나님의 나라가 임하소서\" (마 6:9-10) 임용택 담임목사_2025.2.9"

        title = "[안양감리교회 주일1부예배] \"두려움을 이기는 힘, 절제\" (딤후 1:7-8)_안양감리교회 천주람 목사_2025.2.9"
        title.refineTitle() shouldBe "\"두려움을 이기는 힘, 절제\" (딤후 1:7-8) 천주람 목사_2025.2.9"
    }
}