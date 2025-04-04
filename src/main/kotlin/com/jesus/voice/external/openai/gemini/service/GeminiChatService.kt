package com.jesus.voice.external.openai.gemini.service

import com.jesus.voice.common.exception.GeminiChatException
import com.jesus.voice.common.exception.WordCountException
import com.jesus.voice.common.util.logger
import com.jesus.voice.external.openai.gemini.client.GeminiChatClient
import org.springframework.stereotype.Service

@Service
class GeminiChatService(
    private val geminiChatClient: GeminiChatClient
) {
    private val log by logger()

    @Throws(GeminiChatException::class)
    fun chat(text: String): String =
        runCatching {
            geminiChatClient.chat(text).getOrThrow()
                .candidates.first().content.parts.first().text
                .convertMarkdown()
        }.onFailure {
            log.error(it.message, it)
            throw WordCountException("문장 요약에 실패하였습니다.")
        }.getOrDefault(text)
}

fun String.convertMarkdown(): String {
    val regex = Regex("\\*\\*(.*?)\\*\\*")
    return this.replace("  ", " ")
        .replace(regex, "<strong>$1</strong>")
}
