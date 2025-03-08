package com.jesus.voice.common.handler

import com.jesus.voice.common.dtos.BasicResponse
import com.jesus.voice.common.exception.ApiException
import com.jesus.voice.common.exception.GeminiChatException
import com.jesus.voice.common.exception.TranscriptDisabledException
import com.jesus.voice.common.exception.WordCountClientException
import com.jesus.voice.common.exception.WordCountException
import com.jesus.voice.common.exception.YoutubePlayListExtractException
import com.jesus.voice.common.exception.YoutubeServiceException
import org.springframework.beans.factory.BeanCreationNotAllowedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import java.net.BindException

@RestControllerAdvice(annotations = [RestController::class])
class RestControllerExceptionHandler {
    @ExceptionHandler(
        TranscriptDisabledException::class,
        YoutubeServiceException::class,
        WordCountException::class,
        GeminiChatException::class,
        YoutubePlayListExtractException::class,
        WordCountClientException::class,
        ApiException::class,
    )
    fun handleCustomNotFoundException(ex: Exception): ResponseEntity<BasicResponse<Any>> {
        return BasicResponse.notFound(ex.message ?: "처리가 불가능한 요청입니다.")
    }

    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        MethodArgumentTypeMismatchException::class,
        BindException::class,
        IllegalArgumentException::class,
        MissingServletRequestParameterException::class,
        AccessDeniedException::class,
        MethodArgumentNotValidException::class,
        MissingRequestHeaderException::class,
    )
    fun handleBadRequest(ex: Exception): ResponseEntity<BasicResponse<Any>> {
        return BasicResponse.clientError(ex.message ?: "")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<BasicResponse<Any>> {
        return BasicResponse.internalServerError("An error occurred while processing the request. Please try again.")
    }

    @ExceptionHandler(BeanCreationNotAllowedException::class)
    fun handleBeanCreationNotAllowedException(ex: Exception): ResponseEntity<String> {
        return ResponseEntity("BeanCreationNotAllowedException", HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(ex: Exception): ResponseEntity<BasicResponse<Any>> {
        return BasicResponse.notFound("NoHandlerFoundException")
    }
}