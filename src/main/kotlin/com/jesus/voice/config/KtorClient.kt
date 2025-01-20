package com.jesus.voice.config

import com.jesus.voice.common.exception.YoutubeClientResponseFailException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.xml.xml
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class KtorClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { // JSON, XML 데이터 직렬화 및 역직렬화 플러그인
            json()
            xml()
        }

        install(Logging) { // 요청 및 응답 로그 플러그인
            level = LogLevel.INFO
        }

        install(DefaultRequest) { // 모든 요청에 기본적으로 포함될 헤더
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        install(HttpTimeout) { // 요청 타임아웃
            requestTimeoutMillis = 10_000
        }

        install(HttpRequestRetry) { // 요청 실패 시 재시도
            retryOnException(maxRetries = 3)
        }
    }

    fun get(url: String): HttpResponse {
        return runBlocking {
            client.get(url)
        }
    }

    fun post(url: String, body: Any): HttpResponse {
        return runBlocking {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    @Throws(YoutubeClientResponseFailException::class)
    suspend fun handleResponse(response: HttpResponse): String =
        if (response.status.isSuccess()) {
            response.body()
        } else {
            throw YoutubeClientResponseFailException(response.status.value)
        }
}