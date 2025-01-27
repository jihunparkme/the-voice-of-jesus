package com.jesus.voice.external.openai.gemini.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeminiChatRequest(
    val contents: List<Part>
) {
    constructor(question: String) : this(
        contents = listOf(
            Part(question)
        )
    )
}

@Serializable
data class Part(
    val parts: List<Text>
) {
    constructor(question: String) : this(
        parts = listOf(
            Text(question)
        )
    )
}

@Serializable
data class Text(
    val text: String
)

@Serializable
data class GeminiChatResponse(
    val candidates: List<Candidate>,
    val usageMetadata: UsageMetadata,
    val modelVersion: String
)

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String,
    val avgLogprobs: Double
)

@Serializable
data class Content(
    val parts: List<ResponsePart>,
    val role: String
)

@Serializable
data class ResponsePart(
    val text: String
)

@Serializable
data class UsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int
)
