package com.jesus.voice.youtube.extractor

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.jesus.voice.common.exception.TranscriptDisabledException
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class TranscriptExtractor {
    companion object {
        fun extractTranscript(transcript: String): String =
            Jsoup.parse(transcript, "", Parser.xmlParser())
                .select("text")
                .joinToString("\n") { it.text() }
    }
}

object TranscriptUrlExtractor {
    var objectMapper: ObjectMapper = ObjectMapper()

    fun extractTranscriptUrl(videoId: String, videoPageHtml: String): String {
        val videoDetailHtml = getVideoDetails(videoPageHtml)
        val videoDetailJson = parseJson(videoId, videoDetailHtml)
        return videoDetailJson.get("captionTracks").toString()
    }

    private fun getVideoDetails(videoPageHtml: String): String {
        val splitHtml = videoPageHtml.split("\"captions\":")
        return splitHtml[1].split(",\"videoDetails")[0].replace("\n", "")
    }

    private fun parseJson(videoId: String, json: String): JsonNode {
        val parsedJson = objectMapper.readTree(json).get("playerCaptionsTracklistRenderer")
        if (parsedJson.isNull or !parsedJson.has("captionTracks")) {
            throw TranscriptDisabledException(videoId)
        }
        return parsedJson
    }
}
