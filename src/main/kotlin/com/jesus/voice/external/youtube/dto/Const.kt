package com.jesus.voice.external.youtube.dto

import com.fasterxml.jackson.databind.ObjectMapper

object Const {
    const val YOUTUBE_WATCH_URL: String = "https://www.youtube.com/watch?v="
    const val YOUTUBE_PLAYLIST_URL: String = "https://www.youtube.com/playlist?list="

    val objectMapper: ObjectMapper = ObjectMapper()
}