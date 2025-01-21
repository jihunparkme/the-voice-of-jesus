package com.jesus.voice.common.exception

import com.jesus.voice.config.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ApiException::class)
    protected fun handleApiException(ex: ApiException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ex.errorResponse, HttpStatus.valueOf(ex.errorResponse.code))
    }
}