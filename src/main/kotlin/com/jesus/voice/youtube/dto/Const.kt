package com.jesus.voice.youtube.dto

import com.fasterxml.jackson.databind.ObjectMapper

object Const {
    const val YOUTUBE_WATCH_URL: String = "https://www.youtube.com/watch?v="
    const val YOUTUBE_PLAYLIST_URL: String = "https://www.youtube.com/playlist?list="

    const val WORD_COUNT_TEXT_SUMMARIZE_URL: String = "https://wordcount.com/api/summarize"

    val objectMapper: ObjectMapper = ObjectMapper()
}