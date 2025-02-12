package com.jesus.voice.aggregate.sermon.domain

import com.jesus.voice.aggregate.sermon.dto.SermonListResponse
import com.jesus.voice.aggregate.sermon.dto.SermonViewResponse
import com.jesus.voice.external.komoran.WordCount
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

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
    val createdDt: LocalDateTime,
) {
    fun toListResponse(): SermonListResponse =
        SermonListResponse(
            id = id,
            thumbnailUrl = thumbnailUrl,
            uploadedDate = uploadedDate,
            title = title,
            playList = playList.title,
            publisher = publisher,
            hasTranscript = !transcript.isNullOrEmpty(),
        )

    fun toViewResponse(): SermonViewResponse =
        SermonViewResponse(
            id = id ?: "",
            videoId = videoId,
            videoUrl = videoUrl,
            title = title,
            playList = playList,
            publisher = publisher,
            uploadedDate = uploadedDate,
            transcript = transcript ?: "",
            summary = summary ?: "",
            wordCount = wordCount ?: emptyMap(),
            createdDt = createdDt,
        )
}

data class PlayList(
    val title: String = "",
    val id: String = "",
)