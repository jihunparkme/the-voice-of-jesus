package com.jesus.voice.common.exception

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class TranscriptDisabledException(
    videoId: String,
    message: String = "스크립트 사용이 중지된 동영상입니다."
) : RuntimeException(message) {
    init {
        logger.error("$message url= $YOUTUBE_WATCH_URL$videoId")
    }

    companion object {
        const val YOUTUBE_WATCH_URL: String = "https://www.youtube.com/watch?v="
    }
}