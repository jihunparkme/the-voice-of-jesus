package com.jesus.voice.aggregate.sermon.controller

import com.jesus.voice.aggregate.sermon.dto.SermonRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class IndexController {

    fun index(
        @ModelAttribute sermonRequest: SermonRequest,
        model: Model,
    ): String {
        // TODO: 파라미터로 채널을 받으면 채널에 맞는 플레이 리스트를 반환
        return "index"
    }
}