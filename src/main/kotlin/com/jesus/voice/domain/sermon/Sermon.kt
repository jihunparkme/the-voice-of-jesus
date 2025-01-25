package com.jesus.voice.domain.sermon

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sermons")
class Sermon(
    @Id
    val id: String? = null,
    val videoId: String,
    val thumbnailUrl: String,
    val title: String,
    val publisher: String,
    val streamingTime: String,
    val uploadedDate: String,
    val beforeDate: String,
    val transcript: String,
    val summary: String,
)
