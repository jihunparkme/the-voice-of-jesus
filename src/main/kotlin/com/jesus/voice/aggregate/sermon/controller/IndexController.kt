package com.jesus.voice.aggregate.sermon.controller

import com.jesus.voice.aggregate.sermon.dto.SermonRequest
import com.jesus.voice.aggregate.sermon.service.SermonService
import com.jesus.voice.common.dtos.ChannelType
import com.jesus.voice.common.dtos.getPlayList
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class IndexController(
    private val sermonService: SermonService,
) {
    @GetMapping
    fun index(
        @ModelAttribute param: SermonRequest,
        model: Model,
    ): String {
        val pageable = PageRequest.of(
            param.page, param.size, Sort.by("createdDt").descending(),
        )
        val sermonListPage = sermonService.findSermonList(param, pageable)

        model.apply {
            addAttribute("sermonListPage", sermonListPage)
            addAttribute("page", param.page)
            addAttribute("channels", ChannelType.channelList())
            addAttribute("playList", getPlayList(param.channel))
        }

        return "index"
    }
}