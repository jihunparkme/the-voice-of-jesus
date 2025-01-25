package com.jesus.voice.openai.gemini.service

import com.jesus.voice.common.IntegrationTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import java.nio.file.Files
import java.nio.file.Paths

@Disabled
@ActiveProfiles("local")
@IntegrationTest
class GeminiChatServiceTest(
    private val geminiChatService: GeminiChatService,
) {
    @Test
    fun gemini_chat_test() {
        val resource = javaClass.classLoader.getResource("transcript/transcript.txt")
        val content = Files.readString(Paths.get(resource.toURI()))

        val result = geminiChatService.chat("아래 글은 교회의 설교 내용입니다. 짧게 요약해 주세요.\n\n$content").refine()

        println(result)
    }

    fun String.refine() = this.replace("  ", "\n")
            .replace("\n\n", "\n")
}

