package com.jesus.voice.common.exception

import com.jesus.voice.common.dtos.ErrorResponse
import com.jesus.voice.external.youtube.dto.Const.YOUTUBE_WATCH_URL

class TranscriptDisabledException(
    videoId: String,
    override var message: String = "스크립트 사용이 중지된 동영상입니다."
) : RuntimeException(message) {
    init {
        message = "$message url= $YOUTUBE_WATCH_URL$videoId"
    }
}

class YoutubeServiceException(
    override var message: String,
    throwable: Throwable,
) : RuntimeException(message) {
    init {
        message = "$message error: ${throwable.message ?: ""}"
    }
}

class WordCountException(
    message: String,
) : RuntimeException(message)

class GeminiChatException(
    message: String,
) : RuntimeException(message)

class YoutubePlayListExtractException(
    playListId: String,
    throwable: Throwable,
    override var message: String = "failed playlist extraction."
) : RuntimeException(message) {
    init {
        message = "$message playListId: $playListId, " + "error: ${throwable.message ?: ""}"
    }
}

class WordCountClientException(
    throwable: Throwable,
    override var message: String = "wordcount page request exception."
) : RuntimeException(message) {
    init {
        message = "$message error: ${throwable.message ?: ""}"
    }
}

class ApiException(
    val errorResponse: ErrorResponse,
    val code: Int,
) : RuntimeException()

class NotFoundPlayListChannel(
    override var message: String = "등록되지 않은 플레이리스트입니다."
) : RuntimeException(message)

class NotFoundVideo(
    override var message: String = "영상을 찾을 수 없습니다."
) : RuntimeException(message)