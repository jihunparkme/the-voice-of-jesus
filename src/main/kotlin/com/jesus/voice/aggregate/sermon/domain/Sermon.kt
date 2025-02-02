package com.jesus.voice.aggregate.sermon.domain

import com.jesus.voice.aggregate.sermon.dto.SermonResponse
import com.jesus.voice.external.komoran.WordCount
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sermons")
class Sermon(
    @Id
    val id: String? = null,
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
) {
    fun toResponse(): SermonResponse {
        return SermonResponse(
            id = id,
            thumbnailUrl = thumbnailUrl,
            uploadedDate = uploadedDate,
            title = title,
        )
    }
}

class PlayList(
    val title: String,
    val id: String,
)