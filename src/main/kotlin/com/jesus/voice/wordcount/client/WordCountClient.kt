package com.jesus.voice.wordcount.client

import com.jesus.voice.config.KtorClient
import com.jesus.voice.config.ResponseResult
import com.jesus.voice.config.responseResult
import com.jesus.voice.wordcount.dto.SummarizeRequest
import com.jesus.voice.wordcount.dto.SummarizeResponse
import com.jesus.voice.youtube.dto.Const.WORD_COUNT_TEXT_SUMMARIZE_URL
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class WordCountClient(
    private val ktorClient: KtorClient,
) {
    fun summarize(text: String): ResponseResult<SummarizeResponse> =
        runBlocking {
            ktorClient.post(
                WORD_COUNT_TEXT_SUMMARIZE_URL,
                SummarizeRequest(text)
            ).responseResult<SummarizeResponse>()
        }
}