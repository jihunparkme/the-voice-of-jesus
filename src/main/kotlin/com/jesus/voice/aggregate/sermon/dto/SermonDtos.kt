package com.jesus.voice.aggregate.sermon.dto

import com.jesus.voice.aggregate.sermon.domain.PlayList
import com.jesus.voice.common.dtos.ChannelType
import com.jesus.voice.external.komoran.WordCount

data class SermonRequest(
    val page: Int = 0,
    val size: Int = 10,

    val search: String = "",
    val channel: String = ChannelType.ALL.name,
    val playList: String = ChannelType.ALL.name,
)

data class SermonListResponse(
    val id: String?,
    val thumbnailUrl: String,
    val title: String,
    val uploadedDate: String,
)

data class SermonViewResponse(
    val id: String = "",
    val videoId: String = "",
    val videoUrl: String = "",
    val title: String = "",
    val playList: PlayList = PlayList(),
    val publisher: String = "",
    val streamingTime: String = "",
    val uploadedDate: String = "",
    val beforeDate: String = "",
    val transcript: String = "",
    val summary: String = "",
    val wordCount: WordCount = emptyMap(),
    val createdDt: String = "",
) {
    companion object {
        val EMPTY = SermonViewResponse()
    }
}