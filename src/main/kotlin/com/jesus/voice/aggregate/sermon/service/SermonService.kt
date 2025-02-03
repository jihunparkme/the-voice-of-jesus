package com.jesus.voice.aggregate.sermon.service

import com.jesus.voice.aggregate.sermon.domain.Sermon
import com.jesus.voice.aggregate.sermon.domain.SermonRepository
import com.jesus.voice.aggregate.sermon.dto.SermonRequest
import com.jesus.voice.aggregate.sermon.dto.SermonListResponse
import com.jesus.voice.common.dtos.AYMCPlayList
import com.jesus.voice.common.dtos.ChannelType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SermonService(
    private val sermonRepository: SermonRepository,
) {
    @Transactional(readOnly = true)
    fun findSermonList(
        param: SermonRequest,
        pageable: PageRequest,
    ): Page<SermonListResponse> {
        val request = param.copy(
            channel = param.channel.takeIf { it.isNotBlank() }?.let { ChannelType.from(it).title } ?: param.channel,
            playList = param.playList.takeIf { it.isNotBlank() }?.let { AYMCPlayList.from(it) } ?: param.playList
        )
        val findSermons = sermonRepository.findSermons(request, pageable)
        return findSermons.map(Sermon::toResponse)
    }
}