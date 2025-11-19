package com.jesus.voice.external.openai.gemini.service

import com.google.genai.Client
import com.jesus.voice.common.exception.GeminiChatException
import com.jesus.voice.common.exception.WordCountException
import com.jesus.voice.common.util.logger
import org.springframework.stereotype.Service

@Service
class GeminiChatService(
    private val genaiClient: Client,
) {
    private val log by logger()

    @Throws(GeminiChatException::class)
    fun chat(text: String): String =
        runCatching {
            val response = genaiClient.models.generateContent(
                MODEL_NAME,
                text,
                null
            )

            val answer = response.text()
            if (answer == null) {
                throw WordCountException("문장 요약에 실패하였습니다.")
            }
            return answer
        }.onFailure {
            log.error(it.message, it)
            throw WordCountException("문장 요약에 실패하였습니다.")
        }.getOrDefault(text)

    companion object {
        // https://ai.google.dev/gemini-api/docs/models?hl=ko#model-versions
        private const val MODEL_NAME = "gemini-flash-latest"
    }
}

fun String.convertMarkdown(): String {
    val regex = Regex("\\*\\*(.*?)\\*\\*")
    return this.replace("  ", " ")
        .replace(regex, "<strong>$1</strong>")
}
