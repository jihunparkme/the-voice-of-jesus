package com.jesus.voice.aggregate.sermon.dto

import com.jesus.voice.common.dtos.ChannelType

data class SermonRequest(
    val page: Int = 0,
    val size: Int = 10,

    val search: String = "",
    val channel: String = ChannelType.ALL.name,
    val playList: String = ChannelType.ALL.name,
)

data class SermonResponse(
    val id: String?,
    val thumbnailUrl: String,
    val title: String,
    val uploadedDate: String,
)