package com.jesus.voice.aggregate.sermon.controller

import com.jesus.voice.aggregate.sermon.dto.ExtractVideoRequest
import com.jesus.voice.aggregate.sermon.service.SermonExtractService
import com.jesus.voice.common.annotation.ValidateAdmin
import com.jesus.voice.common.dtos.AYMCPlayList
import com.jesus.voice.common.dtos.BasicResponse
import com.jesus.voice.common.dtos.ChannelType
import com.jesus.voice.common.dtos.Result
import com.jesus.voice.common.exception.NotFoundPlayListChannel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sermon/admin")
class AdminController(
    val sermonExtractService: SermonExtractService,
) {
    @ValidateAdmin
    @PostMapping("/extract/video")
    fun extractVideo(
        @RequestBody request: ExtractVideoRequest,
    ): ResponseEntity<BasicResponse<Result>> {
        val playListChannel = when (request.playListChannel) {
            ChannelType.AYMC.name -> AYMCPlayList.fromById(request.playListChannelId)
            else -> throw NotFoundPlayListChannel()
        }
        sermonExtractService.extractSermon(playListChannel, request.playListChannelId, request.count)
        return BasicResponse.ok(Result.SUCCESS)
    }
}