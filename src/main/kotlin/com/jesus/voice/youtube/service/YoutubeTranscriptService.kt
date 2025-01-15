package com.jesus.voice.youtube.service

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

    fun getTranscript(videoId: VideoId): String =
        runCatching {
            val videoPage = youtubeClient.getVideoPage(videoId.id)
            val transcriptUrl = extractTranscriptUrl(videoId.id, videoPage)
            val transcriptXml = youtubeClient.getTranscript(videoId.id, transcriptUrl)
            TranscriptExtractor.extractTranscript(transcriptXml)
        }.onFailure {
            log.error(
                "YoutubeTranscriptService getTranscript exception. videoId: $videoId, error: ${it.message}",
                it
            )
        }.getOrElse { "" }
}