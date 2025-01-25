package com.jesus.voice.domain.sermon

import com.jesus.voice.komoran.WordCount
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sermons")
class Sermon(
    @Id
    val id: String? = null,
    val videoId: String,
    val thumbnailUrl: String,
    val title: String,
    val channel: Channel,
    val publisher: String,
    val streamingTime: String,
    val uploadedDate: String,
    val beforeDate: String,
    val transcript: String? = null,
    val summary: String? = null,
    val wordCount: WordCount? = null,
)

class Channel(
    val title: String,
    val id: String,
)