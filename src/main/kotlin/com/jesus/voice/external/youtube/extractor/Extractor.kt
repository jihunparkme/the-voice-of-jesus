package com.jesus.voice.external.youtube.extractor

import com.fasterxml.jackson.databind.JsonNode
import com.jesus.voice.common.exception.TranscriptDisabledException
import com.jesus.voice.common.exception.YoutubePlayListExtractException
import com.jesus.voice.external.youtube.dto.Const.YOUTUBE_WATCH_URL
import com.jesus.voice.external.youtube.dto.Const.objectMapper
import com.jesus.voice.external.youtube.dto.PlayListVideo
import com.jesus.voice.external.youtube.dto.Transcript
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import kotlin.jvm.Throws

object TranscriptUrlExtractor {
    fun extractTranscriptUrl(videoId: String, videoPageHtml: String): String {
        val videoDetailHtml = getVideoDetails(videoId, videoPageHtml)
        val videoDetailJson = parseJson(videoId, videoDetailHtml)
        val transcriptList = videoDetailJson["captionTracks"].map {
            Transcript(
                baseUrl = it["baseUrl"].asText(),
                name = it["name"]["simpleText"].asText(),
                languageCode = it["languageCode"].asText(),
            )
        }

        return transcriptList.firstOrNull { it.languageCode == "ko" }?.baseUrl
            ?: transcriptList.first().baseUrl
    }

    private fun getVideoDetails(videoId: String, html: String): String =
        html.split("\"captions\":")
            .getOrNull(1)
            ?.substringBefore(",\"videoDetails")
            ?.replace("\n", "")
            ?: throw TranscriptDisabledException(videoId)

    @Throws(TranscriptDisabledException::class)
    private fun parseJson(videoId: String, html: String): JsonNode {
        val parsedJson = objectMapper.readTree(html)["playerCaptionsTracklistRenderer"]
        if (parsedJson.isNull or !parsedJson.has("captionTracks")) {
            throw TranscriptDisabledException(videoId)
        }
        return parsedJson
    }
}

object TranscriptExtractor {
    fun extractTranscript(transcript: String): String =
        Jsoup.parse(transcript, "", Parser.xmlParser())
            .select("text")
            .joinToString("\n") { it.text() }
}

object PlayListExtractor {
    @Throws(YoutubePlayListExtractException::class)
    fun extractPlayList(playListId: String, playListHtml: String): List<PlayListVideo> =
        runCatching {
            val playListVideosHtml = getPlayListVideos(playListHtml)
            return parseJson(playListVideosHtml)
        }.onFailure {
            throw YoutubePlayListExtractException(playListId, it)
        }.getOrDefault(emptyList())

    private fun getPlayListVideos(playListHtml: String): String {
        val splitHtml = playListHtml.split("\"twoColumnBrowseResultsRenderer\":")
        val playlistVideosHtml = splitHtml[1].split(".\"frameworkUpdates\"")
        return playlistVideosHtml[0].replace("\n", "")
    }

    private fun parseJson(html: String): List<PlayListVideo> {
        val parsedJson = objectMapper.readTree(html)
        return parsedJson["tabs"]
            ?.firstOrNull()?.get("tabRenderer")
            ?.get("content")?.get("sectionListRenderer")
            ?.get("contents")?.firstOrNull()
            ?.get("itemSectionRenderer")?.get("contents")?.firstOrNull()
            ?.get("playlistVideoListRenderer")
            ?.get("contents")?.map() {
                val video = it.get("playlistVideoRenderer")
                val title = video?.get("title")?.get("runs")?.first()?.get("text")?.asText() ?: ""
                PlayListVideo(
                    videoId = video?.get("videoId")?.asText() ?: "",
                    videoUrl = YOUTUBE_WATCH_URL + video,
                    thumbnailUrl = video?.get("thumbnail")?.last()?.last()?.get("url")?.asText() ?: "",
                    title = title,
                    publisher = video?.get("shortBylineText")?.get("runs")?.first()?.get("text")?.asText() ?: "",
                    streamingTime = video?.get("lengthText")?.get("simpleText")?.asText() ?: "",
                    uploadedDate = title.split("_").last(),
                    beforeDate = video?.get("videoInfo")?.get("runs")?.last()?.get("text")?.asText()?.split(": ")?.last() ?: "",
                )
            }.orEmpty()
    }
}