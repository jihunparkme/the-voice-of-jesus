package com.jesus.voice.aggregate.sermon.controller

import com.jesus.voice.aggregate.sermon.service.SermonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/sermon")
class SermonViewController(
    private val sermonService: SermonService,
) {
    @GetMapping("/{id}")
    fun view(
        @PathVariable(name = "id") id: String,
        model: Model,
    ): String {
        val sermonResponse = sermonService.findSermonView(id)
        model.addAttribute("sermon", sermonResponse)
        return "sermon-view"
    }
}