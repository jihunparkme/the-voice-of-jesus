package com.jesus.voice.aggregate.sermon.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/sermon")
class SermonViewController {
    @GetMapping("/{id}")
    fun view(
        @PathVariable id: String, model: Model,
    ): String {
        return "sermon-view"
    }
}