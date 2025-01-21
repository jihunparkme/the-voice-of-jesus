package com.jesus.voice.wordcount.dto

data class SummarizeRequest(
    val text: String,
    val locale: String = "ko",
)

data class SummarizeResponse(
    val mac: String,
    val content: String,
)