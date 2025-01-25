package com.jesus.voice.domain.sermon

import org.springframework.data.mongodb.repository.MongoRepository

interface SermonRepository: MongoRepository<Sermon, String> {
    fun existsByVideoId(videoId: String): Boolean
}