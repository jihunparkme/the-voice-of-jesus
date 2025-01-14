package com.jesus.voice.youtube.extractor

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.jesus.voice.common.exception.TranscriptDisabledException
import com.jesus.voice.youtube.dto.Transcript
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

object TranscriptUrlExtractor {
    private var objectMapper: ObjectMapper = ObjectMapper()

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

    private fun getVideoDetails(videoPageHtml: String): String {
        val splitHtml = videoPageHtml.split("\"captions\":")
        return splitHtml[1].split(",\"videoDetails")[0].replace("\n", "")
    }

    private fun parseJson(videoId: String, json: String): JsonNode {
        val parsedJson = objectMapper.readTree(json)["playerCaptionsTracklistRenderer"]
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