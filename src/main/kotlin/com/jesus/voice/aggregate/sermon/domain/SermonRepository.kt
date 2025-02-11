package com.jesus.voice.aggregate.sermon.domain

import com.jesus.voice.aggregate.sermon.dto.SermonRequest
import com.jesus.voice.common.util.dotPath
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

interface SermonRepository : MongoRepository<Sermon, String>, SermonRepositoryCustom {
    fun existsByVideoId(videoId: String): Boolean
}

interface SermonRepositoryCustom {
    fun findSermons(param: SermonRequest, pageable: PageRequest): Page<Sermon>
}

@Repository
class SermonRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate,
) : SermonRepositoryCustom {

    override fun findSermons(param: SermonRequest, pageable: PageRequest): Page<Sermon> {
        val criteria = buildList {
            param.search.takeIf { it.isNotBlank() }?.let {
                add(Criteria.where(dotPath(Sermon::title)).regex(".*$it.*", "i"))
            }
            param.channel.takeIf { it.isNotBlank() }?.let {
                add(Criteria.where(dotPath(Sermon::publisher)).isEqualTo(it))
            }
            param.playList.takeIf { it.isNotBlank() }?.let {
                add(Criteria.where(dotPath(Sermon::playList, PlayList::title)).isEqualTo(it))
            }
        }

        val query = Query().apply {
            if (criteria.isNotEmpty()) addCriteria(Criteria().andOperator(*criteria.toTypedArray()))
            with(pageable)
        }

        val total = mongoTemplate.count(Query().apply {
            if (criteria.isNotEmpty()) addCriteria(Criteria().andOperator(*criteria.toTypedArray()))
        }, Sermon::class.java)

        val sermons = mongoTemplate.find(query.with(pageable), Sermon::class.java)

        return PageImpl(sermons, pageable, total)
    }
}