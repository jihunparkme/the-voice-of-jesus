package com.jesus.voice.common.exception

import com.jesus.voice.common.util.logger
import com.jesus.voice.youtube.dto.Const.YOUTUBE_WATCH_URL

class TranscriptDisabledException(
    videoId: String,
    message: String = "스크립트 사용이 중지된 동영상입니다."
) : RuntimeException(message) {
    init {
        logger.error("$message url= $YOUTUBE_WATCH_URL$videoId")
    }
}