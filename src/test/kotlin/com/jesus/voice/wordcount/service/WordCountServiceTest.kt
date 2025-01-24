package com.jesus.voice.wordcount.service

import com.jesus.voice.common.IntegrationTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.nio.file.Paths

@IntegrationTest
class WordCountServiceTest(
    private val wordCountService: WordCountService,
) : BehaviorSpec({
    given("요약이 필요한 문장을 요청할 경우") {
        val resource = javaClass.classLoader.getResource("transcript/transcript.txt")
        val content = Files.readString(Paths.get(resource.toURI()))

        Then("요약된 문장을 반환해 준다.") {
            val result = wordCountService.summarizeText(content)
            result shouldBe ""
        }
    }
})