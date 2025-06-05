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
    val candidates: List<Candidate> = emptyList(),
    val usageMetadata: UsageMetadata? = null,
    val modelVersion: String = ""
)

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String = "",
    val avgLogprobs: Double = 0.0
)

@Serializable
data class Content(
    val parts: List<ResponsePart> = emptyList(),
    val role: String = ""
)

@Serializable
data class ResponsePart(
    val text: String = ""
)

@Serializable
data class UsageMetadata(
    val promptTokenCount: Int = 0,
    val candidatesTokenCount: Int = 0,
    val totalTokenCount: Int = 0
)
