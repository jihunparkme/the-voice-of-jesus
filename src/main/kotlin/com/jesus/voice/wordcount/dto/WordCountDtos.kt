package com.jesus.voice.wordcount.dto

data class SummarizeRequest(
    val text: String,
    val locale: String = "ko",
)