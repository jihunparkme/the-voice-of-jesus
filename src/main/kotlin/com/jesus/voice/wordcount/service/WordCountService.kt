package com.jesus.voice.wordcount.service

import com.jesus.voice.common.exception.WordCountException
import com.jesus.voice.common.util.logger
import com.jesus.voice.wordcount.client.WordCountClient
import org.springframework.stereotype.Service

@Service
class WordCountService(
    private val wordCountClient: WordCountClient,
) {
    private val log by logger()

    @Throws(WordCountException::class)
    fun summarizeText(text: String): String =
        runCatching {
            wordCountClient.summarize(text).getOrThrow().content
        }.onFailure {
            log.error(it.message, it)
            throw WordCountException("문장 요약에 실패하였습니다.")
        }.getOrDefault(text)
}