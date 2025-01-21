package com.jesus.voice.youtube.service

import com.jesus.voice.common.exception.YoutubeTranscriptException
import com.jesus.voice.common.util.logger
import com.jesus.voice.youtube.client.YoutubeClient
import com.jesus.voice.youtube.dto.VideoId
import com.jesus.voice.youtube.extractor.TranscriptExtractor
import com.jesus.voice.youtube.extractor.TranscriptUrlExtractor.extractTranscriptUrl
import org.springframework.stereotype.Service

@Service
class YoutubeTranscriptService(
    private val youtubeClient: YoutubeClient,
) {
    private val log by logger()

    @Throws(YoutubeTranscriptException::class)
    fun getTranscript(videoId: VideoId): Result<String> =
        runCatching {
            val videoPage = youtubeClient.getVideoPage(videoId.id)
            val transcriptUrl = extractTranscriptUrl(videoId.id, videoPage.getOrThrow())
            val transcriptXml = youtubeClient.getTranscript(videoId.id, transcriptUrl)
            TranscriptExtractor.extractTranscript(transcriptXml.getOrThrow())
        }.onFailure {
            log.error(it.message, it)
            throw YoutubeTranscriptException("동영상 자막 추출에 실패하였습니다. videoId: $videoId")
        }
}