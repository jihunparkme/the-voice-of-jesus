package com.jesus.voice.ktor

import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
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
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.xml.xml
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test

class KtorClientTest {
    @Test
    fun ktorClientTest() {
        val client = HttpClient(CIO) {
            // JSON, XML 데이터 직렬화 및 역직렬화 플러그인
            install(ContentNegotiation) {
                json()
                xml()
            }

            // 요청 및 응답 로그 플러그인
            install(Logging) {
                level = LogLevel.INFO
            }

            // 모든 요청에 기본적으로 포함될 헤더
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }

            // 요청 타임아웃
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000
            }

            // 요청 실패 시 재시도
            install(HttpRequestRetry) {
                retryOnException(maxRetries = 3)
            }
        }

        runBlocking {
            val response: MyResponse = client.get("https://jsonplaceholder.typicode.com/posts/1").body()
            response shouldBe MyResponse(
                userId = 1,
                id = 1,
                title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body = "quia et suscipit\n" +
                    "suscipit recusandae consequuntur expedita et cum\n" +
                    "reprehenderit molestiae ut ut quas totam\n" +
                    "nostrum rerum est autem sunt rem eveniet architecto",
            )
        }

        client.close()
    }
}

@Serializable
data class MyResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)