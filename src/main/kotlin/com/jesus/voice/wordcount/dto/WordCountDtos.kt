package com.jesus.voice.wordcount.dto

import kotlinx.serialization.Serializable

@Serializable
data class SummarizeRequest(
    val text: String,
    val locale: String = "ko",
)

@Serializable
data class SummarizeResponse(
    val mac: String,
    val content: String,
)