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

data class SermonResponse(
    val videoId: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val title: String,
    val playList: PlayList,
    val publisher: String,
    val streamingTime: String,
    val uploadedDate: String,
    val beforeDate: String,
    val transcript: String? = null,
    val summary: String? = null,
    val wordCount: WordCount? = null,
    val createdDt: String,
)