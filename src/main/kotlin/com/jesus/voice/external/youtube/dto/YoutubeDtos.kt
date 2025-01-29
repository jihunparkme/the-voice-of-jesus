package com.jesus.voice.external.youtube.dto

import com.jesus.voice.aggregate.sermon.domain.Channel
import com.jesus.voice.aggregate.sermon.domain.Sermon
import com.jesus.voice.external.komoran.WordCount

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
    val videoUrl: String,
    val thumbnailUrl: String,
    val title: String,
    val publisher: String,
    val streamingTime: String,
    val uploadedDate: String,
    val beforeDate: String,
    val transcript: String = "",
    val summary: String = "",
    val createdDt: String,
) {
    fun toSermon(
        channel: Channel,
        transcript: String = "",
        summary: String = "",
        wordCount: WordCount = mapOf()
    ): Sermon = Sermon(
        videoId = this.videoId,
        videoUrl = this.videoUrl,
        thumbnailUrl = this.thumbnailUrl,
        title = this.title,
        channel = channel,
        publisher = this.publisher,
        streamingTime = this.streamingTime,
        uploadedDate = this.uploadedDate,
        beforeDate = this.beforeDate,
        transcript = transcript,
        summary = summary,
        wordCount = wordCount,
        createdDt = this.createdDt,
    )
}