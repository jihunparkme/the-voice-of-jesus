package com.jesus.voice.youtube.service

import com.jesus.voice.common.exception.YoutubeServiceException
import com.jesus.voice.common.util.logger
import com.jesus.voice.youtube.client.YoutubeClient
import com.jesus.voice.youtube.dto.VideoId
import com.jesus.voice.youtube.extractor.TranscriptExtractor
import com.jesus.voice.youtube.extractor.TranscriptUrlExtractor.extractTranscriptUrl
import org.springframework.stereotype.Service

@Service
class YoutubeService(
    private val youtubeClient: YoutubeClient,
) {
    private val log by logger()

    @Throws(YoutubeServiceException::class)
    fun getTranscript(videoId: VideoId): String =
        runCatching {
            val videoPage = youtubeClient.getVideoPage(videoId.id).getOrThrow()
            val transcriptUrl = extractTranscriptUrl(videoId.id, videoPage)
            val transcriptXml = youtubeClient.getTranscript(videoId.id, transcriptUrl).getOrThrow()
            TranscriptExtractor.extractTranscript(transcriptXml)
        }.onFailure {
            log.error(it.message, it)
            throw YoutubeServiceException("동영상 자막 추출에 실패하였습니다. videoId: $videoId")
        }.getOrDefault("")
}