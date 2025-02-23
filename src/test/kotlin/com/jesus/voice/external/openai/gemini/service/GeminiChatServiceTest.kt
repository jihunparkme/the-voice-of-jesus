package com.jesus.voice.external.openai.gemini.service

import com.jesus.voice.common.IntegrationTest
import com.jesus.voice.external.openai.gemini.service.GeminiChatService
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

@IntegrationTest
class GeminiChatServiceTest(
    private val geminiChatService: GeminiChatService,
) {
    @Test
    @Disabled
    fun gemini_chat_test() {
        val resource = javaClass.classLoader.getResource("transcript/transcript.txt")
        val content = Files.readString(Paths.get(resource.toURI()))

        val result = geminiChatService.chat("아래 글은 교회의 설교 내용입니다. 내용을 요약해 주세요.\n\n$content")

        println(result)
    }

    @Test
    fun refined_content_test() {
        val text = """
            **2. 하나님의 뜻: 생명을 살리고 거룩하게 사는 것:** 설교자는 하나님의 뜻을 크게 두 가지로 설명합니다. 첫째는 **생명을 살리는 것**입니다. 하나님은 모든 사람이 구원받기를 원하시며, 예수님은 이를 위해 이 땅에 오셔서 병든 자, 가난한 자, 소외된 자들을 살리셨습니다. 교회의 목적도 생명을 살리는 것이며, 성도는 이 일에 참여해야 합니다. 둘째는 **거룩한 삶**입니다. 십계명을 통해 제시된 하나님의 뜻은 하나님을 사랑하고 이웃을 사랑하는 것이며, 이는 곧 사랑과 용서, 진실, 평화를 실천하는 삶입니다. 이는 율법주의적인 삶이 아닌, 항상 기뻐하고 기도하며 감사하는 삶입니다.
        """.trimIndent()

        text.convertMarkdown() shouldBe """
            <strong>2. 하나님의 뜻: 생명을 살리고 거룩하게 사는 것:</strong> 설교자는 하나님의 뜻을 크게 두 가지로 설명합니다. 첫째는 <strong>생명을 살리는 것</strong>입니다. 하나님은 모든 사람이 구원받기를 원하시며, 예수님은 이를 위해 이 땅에 오셔서 병든 자, 가난한 자, 소외된 자들을 살리셨습니다. 교회의 목적도 생명을 살리는 것이며, 성도는 이 일에 참여해야 합니다. 둘째는 <strong>거룩한 삶</strong>입니다. 십계명을 통해 제시된 하나님의 뜻은 하나님을 사랑하고 이웃을 사랑하는 것이며, 이는 곧 사랑과 용서, 진실, 평화를 실천하는 삶입니다. 이는 율법주의적인 삶이 아닌, 항상 기뻐하고 기도하며 감사하는 삶입니다.
        """.trimIndent()
    }
}

