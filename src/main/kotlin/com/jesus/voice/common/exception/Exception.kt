package com.jesus.voice.common.exception

import com.jesus.voice.youtube.dto.Const.YOUTUBE_WATCH_URL

class TranscriptDisabledException(
    videoId: String,
    override var message: String = "스크립트 사용이 중지된 동영상입니다."
) : RuntimeException(message) {
    init {
        message = "$message url= $YOUTUBE_WATCH_URL$videoId"
    }
}

class YoutubeClientException(
    type: String,
    id: String,
    throwable: Throwable,
    override var message: String = "youtube page request exception."
) : RuntimeException(message) {
    init {
        message = "$message $type: $id, " + "error: ${throwable.message ?: ""}"
    }
}