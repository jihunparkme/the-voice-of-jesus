package com.jesus.voice.youtube.extractor

import com.fasterxml.jackson.databind.JsonNode
import com.jesus.voice.common.exception.TranscriptDisabledException
import com.jesus.voice.youtube.dto.Const.objectMapper
import com.jesus.voice.youtube.dto.Transcript
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

object TranscriptUrlExtractor {
    fun extractTranscriptUrl(videoId: String, videoPageHtml: String): String {
        val videoDetailHtml = getVideoDetails(videoPageHtml)
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

    private fun getVideoDetails(html: String): String {
        val splitHtml = html.split("\"captions\":")
        return splitHtml[1].split(",\"videoDetails")[0].replace("\n", "")
    }

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
    fun extractPlayList(playListId: String, playListHtml: String): String {
        val playListDetailHtml = getPlayListDetail(playListId, playListHtml)
        return parseJson(playListId, playListDetailHtml)
    }

    private fun getPlayListDetail(playListId: String, playListHtml: String): String {
        val splitHtml = playListHtml.split("\"twoColumnBrowseResultsRenderer\":")
        val playlistDetail = splitHtml[1].split(".\"frameworkUpdates\"")
        return playlistDetail[0].replace("\n", "")
    }

    private fun parseJson(playListId: String, html: String): String {
        val parsedJson = objectMapper.readTree(html)
        return parsedJson.toString()
    }
}