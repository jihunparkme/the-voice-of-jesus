package com.jesus.voice.config

import com.google.genai.Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GenaiConfig(
    @Value("\${gemini.apikey}") private val apiKey: String,
) {
    @Bean
    fun genaiClient(): Client {
        return Client.builder()
            .apiKey(apiKey)
            .build()
    }
}