package com.jesus.voice.external.openai.gemini.client

import com.jesus.voice.config.KtorClient
import com.jesus.voice.common.dtos.ResponseResult
import com.jesus.voice.config.responseResult
import com.jesus.voice.external.openai.gemini.dto.GeminiChatRequest
import com.jesus.voice.external.openai.gemini.dto.GeminiChatResponse
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GeminiChatClient(
    @Value("\${gemini.api}") private val url: String,
    @Value("\${gemini.apikey}") private val apiKey: String,
    private val ktorClient: KtorClient,
) {
    fun chat(question: String): ResponseResult<GeminiChatResponse> =
        runBlocking {
            ktorClient.post(
                url + apiKey,
                GeminiChatRequest(question)
            ).responseResult<GeminiChatResponse>()
        }
}