package com.jesus.voice.aggregate.sermon.service

import com.jesus.voice.aggregate.sermon.domain.Sermon
import com.jesus.voice.aggregate.sermon.domain.SermonRepository
import com.jesus.voice.aggregate.sermon.dto.SermonRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SermonService(
    private val sermonRepository: SermonRepository,
) {
    @Transactional(readOnly = true)
    fun findSermons(
        param: SermonRequest,
        pageable: PageRequest,
    ) : Page<Sermon> = sermonRepository.findSermons(param, pageable)
}