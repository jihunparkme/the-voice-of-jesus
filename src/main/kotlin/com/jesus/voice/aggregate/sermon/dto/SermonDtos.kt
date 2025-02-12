package com.jesus.voice.aggregate.sermon.dto

import com.jesus.voice.aggregate.sermon.domain.PlayList
import com.jesus.voice.common.dtos.ChannelType
import com.jesus.voice.external.komoran.WordCount
import java.time.LocalDateTime

data class SermonRequest(
    val page: Int = 0,
    val size: Int = 8,

    val search: String = "",
    val channel: String = ChannelType.ALL.name,
    val playList: String = ChannelType.ALL.name,
)

data class SermonListResponse(
    val id: String?,
    val thumbnailUrl: String,
    val title: String,
    val uploadedDate: String,
    val publisher: String,
    val playList: String,
    val hasTranscript: Boolean,
)

data class SermonViewResponse(
    val id: String = "",
    val videoId: String = "",
    val videoUrl: String = "",
    val title: String = "",
    val playList: PlayList = PlayList(),
    val publisher: String = "",
    val uploadedDate: String = "",
    val transcript: String = "",
    val summary: String = "",
    val wordCount: WordCount = emptyMap(),
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        val EMPTY = SermonViewResponse()
    }
}