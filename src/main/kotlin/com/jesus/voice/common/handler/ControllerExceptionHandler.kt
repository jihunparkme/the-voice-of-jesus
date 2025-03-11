package com.jesus.voice.common.handler

import com.jesus.voice.common.exception.NotFoundPlayListChannel
import com.jesus.voice.common.exception.NotFoundVideo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice(annotations = [Controller::class])
class ControllerExceptionHandler {

    @ExceptionHandler(
        NoHandlerFoundException::class,
        NotFoundPlayListChannel::class,
        NotFoundVideo::class
    )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: Exception): String {
        return "error/4xx"
    }
}