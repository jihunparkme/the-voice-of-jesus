package com.jesus.voice.aggregate.sermon.dto

data class SermonRequest(
    val page: Int = 0,
    val size: Int = 10,

    val search: String = "",
    val channel: String = "",
    val playList: String = "",
)