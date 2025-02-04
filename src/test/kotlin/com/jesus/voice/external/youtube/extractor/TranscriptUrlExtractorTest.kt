package com.jesus.voice.external.youtube.extractor

import com.jesus.voice.common.exception.TranscriptDisabledException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.nio.file.Paths

class TranscriptUrlExtractorTest : BehaviorSpec({
    Given("영상 페이지 컨텐츠가 전달되면") {
        val VIDEO_ID = "AA1234"

        When("자막이 있는 영상일 경우") {
            val resource = javaClass.classLoader.getResource("html/video-page.txt")
            val content = Files.readString(Paths.get(resource!!.toURI()))

            Then("transcript url 추출") {
                val result = TranscriptUrlExtractor.extractTranscriptUrl(VIDEO_ID, content)
                result shouldBe "https://www.youtube.com/api/timedtext?v=ekr2nIex040&ei=5HmDZ6zgI5qtvcAP3M3_wAo&caps=asr&opi=112496729&xoaf=5&hl=ko&ip=0.0.0.0&ipbits=0&expire=1736694868&sparams=ip,ipbits,expire,v,ei,caps,opi,xoaf&signature=6584405AAB01B1315F8E4D04A8F541D1F5CDD80E.D9B2B4534C4321320FB5A31914ED4772B816F746&key=yt8&kind=asr&lang=en"
            }
        }

        When("자막이 없는 영상일 경우") {
            val resource = javaClass.classLoader.getResource("html/video-page-non-transcript.txt")
            val content = Files.readString(Paths.get(resource!!.toURI()))

            Then("예외 발생") {
                shouldThrow<TranscriptDisabledException> {
                    TranscriptUrlExtractor.extractTranscriptUrl(VIDEO_ID, content)
                }
            }
        }
    }
})