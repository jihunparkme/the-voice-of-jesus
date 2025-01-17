package com.jesus.voice.youtube.dto

data class VideoId(
    val id: String
) {
    init {
        require(id.matches(Regex("[a-zA-Z0-9_-]{11}"))) { "Invalid video id: $id" }
    }
}

data class Transcript(
    val baseUrl: String,
    val name: String,
    val languageCode: String,
)

data class PlayListVideo(
    val videoId: String,
    val thumbnailUrl: String,
    val title: String,
    val publisher: String,
    val streamingTime: String,
    val uploadedDate: String,
    val beforeDate: String,
)