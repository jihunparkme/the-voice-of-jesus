package com.jesus.voice.external.youtube.dto

import com.fasterxml.jackson.databind.ObjectMapper

object Const {
    const val YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v="
    const val YOUTUBE_TRANSCRIPT_URL = "https://www.youtube.com/youtubei/v1/get_transcript"
    const val YOUTUBE_PLAYLIST_URL = "https://www.youtube.com/playlist?list="

    val objectMapper: ObjectMapper = ObjectMapper()
}