package com.jesus.voice.config

import com.jesus.voice.common.IntegrationTest
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import org.junit.jupiter.api.Test

@IntegrationTest
class KtorClientTest(
    private val ktorClient: KtorClient,
) {
    @Test
    fun extractYoutubeTranscript() {
        runBlocking {
            val response: HttpResponse = ktorClient.get("https://jsonplaceholder.typicode.com/posts/1")

            response.status.value shouldBe 200
            response.status.description shouldBe "OK"
            response.body<String>() shouldBe """
                {
                  "userId": 1,
                  "id": 1,
                  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                  "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                }
            """.trimIndent()
        }
    }
}