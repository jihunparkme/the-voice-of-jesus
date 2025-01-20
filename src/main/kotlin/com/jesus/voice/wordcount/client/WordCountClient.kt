package com.jesus.voice.wordcount.client

import com.jesus.voice.common.exception.WordCountClientException
import com.jesus.voice.config.KtorClient
import com.jesus.voice.wordcount.dto.SummarizeRequest
import com.jesus.voice.youtube.dto.Const.WORD_COUNT_TEXT_SUMMARIZE_URL
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class WordCountClient(
    private val ktorClient: KtorClient,
) {
    @Throws(WordCountClientException::class)
    fun summarizeSentence(text: String): Result<String> =
        runCatching {
            runBlocking {
                val response = ktorClient.post(
                    WORD_COUNT_TEXT_SUMMARIZE_URL,
                    SummarizeRequest(text)
                )
                ktorClient.handleResponse(response)
            }
        }.onFailure {
            throw WordCountClientException(it)
        }
}