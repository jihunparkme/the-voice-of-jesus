package com.jesus.voice.aggregate.sermon.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface SermonRepository: MongoRepository<Sermon, String> {
    fun existsByVideoId(videoId: String): Boolean
}