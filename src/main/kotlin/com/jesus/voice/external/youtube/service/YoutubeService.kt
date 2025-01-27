package com.jesus.voice.external.youtube.service

import com.jesus.voice.common.exception.YoutubeServiceException
import com.jesus.voice.external.youtube.client.YoutubeClient
import com.jesus.voice.external.youtube.dto.PlayListVideo
import com.jesus.voice.external.youtube.dto.VideoId
import com.jesus.voice.external.youtube.extractor.PlayListExtractor
import com.jesus.voice.external.youtube.extractor.TranscriptExtractor
import com.jesus.voice.external.youtube.extractor.TranscriptUrlExtractor.extractTranscriptUrl
import org.springframework.stereotype.Service

@Service
class YoutubeService(
    private val youtubeClient: YoutubeClient,
) {
    @Throws(YoutubeServiceException::class)
    fun getTranscript(videoId: VideoId): String =
        runCatching {
            val videoPage = youtubeClient.getVideoPage(videoId.id).getOrThrow()
            val transcriptUrl = extractTranscriptUrl(videoId.id, videoPage)
            val transcriptXml = youtubeClient.getTranscript(videoId.id, transcriptUrl).getOrThrow()
            TranscriptExtractor.extractTranscript(transcriptXml)
        }.onFailure {
            throw YoutubeServiceException("동영상 자막 추출에 실패하였습니다. videoId: $videoId", it)
        }.getOrDefault("")

    @Throws(YoutubeServiceException::class)
    fun getVideoIdFromPlayList(playListId: String): List<PlayListVideo> =
        runCatching {
            val playListHtml = youtubeClient.getPlayList(playListId).getOrThrow()
            PlayListExtractor.extractPlayList(playListId, playListHtml)
        }.onFailure {
            throw YoutubeServiceException("재생목록 동영상 추출에 실패하였습니다. playListId: $playListId", it)
        }.getOrDefault(emptyList())
}