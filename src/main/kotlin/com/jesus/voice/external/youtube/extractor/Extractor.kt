package com.jesus.voice.external.youtube.extractor

import com.fasterxml.jackson.databind.JsonNode
import com.jesus.voice.common.exception.TranscriptDisabledException
import com.jesus.voice.common.exception.YoutubePlayListExtractException
import com.jesus.voice.external.youtube.client.TranscriptResponse
import com.jesus.voice.external.youtube.dto.Const.YOUTUBE_WATCH_URL
import com.jesus.voice.external.youtube.dto.Const.objectMapper
import com.jesus.voice.external.youtube.dto.PlayListVideo
import com.jesus.voice.external.youtube.dto.Transcript
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.time.LocalDate
import java.time.LocalDateTime

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

    fun extractTranscriptParam(videoId: String, videoPageHtml: String): String {
        val scriptElement = Jsoup.parse(videoPageHtml).select("script").find {
            it.data().contains("var ytInitialData =")
        } ?: return ""

        val scriptContent = scriptElement.data()
        val jsonString =
            scriptContent.substringAfter("var ytInitialData =").substringBeforeLast(";</script>") // 실제 패턴에 맞게 조정 필요
                .trim()
                .removeSuffix(";")

        return try {
            val params =
                objectMapper.readTree(jsonString.split("getTranscriptEndpoint\":")[1]).path("params").asText()
            return params
        } catch (e: Exception) {
            println("Error extracting desired text: ${videoId}, ${e.message}")
            ""
        }
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

    fun extractJsonTranscript(transcriptJson: TranscriptResponse): String {
        return transcriptJson.actions
            ?.asSequence()
            ?.mapNotNull { it.updateEngagementPanelAction }
            ?.mapNotNull { it.content }
            ?.mapNotNull { it.transcriptRenderer }
            ?.mapNotNull { it.content }
            ?.mapNotNull { it.transcriptSearchPanelRenderer }
            ?.mapNotNull { it.body }
            ?.mapNotNull { it.transcriptSegmentListRenderer }
            ?.flatMap { it.initialSegments.orEmpty() }
            ?.mapNotNull { it.transcriptSegmentRenderer }
            ?.mapNotNull { it.snippet }
            ?.flatMap { it.runs.orEmpty() }
            ?.mapNotNull { it.text }
            ?.joinToString("\n")
            ?: ""
    }
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
                val videoId = video?.get("videoId")?.asText() ?: ""
                val beforeDate =
                    video?.get("videoInfo")?.get("runs")?.last()?.get("text")?.asText()?.split(": ")?.last() ?: ""
                PlayListVideo(
                    videoId = videoId,
                    videoUrl = YOUTUBE_WATCH_URL + videoId,
                    thumbnailUrl = video?.get("thumbnail")?.last()?.last()?.get("url")?.asText() ?: "",
                    title = title.refineTitle(),
                    publisher = video?.get("shortBylineText")?.get("runs")?.first()?.get("text")?.asText() ?: "",
                    streamingTime = video?.get("lengthText")?.get("simpleText")?.asText() ?: "",
                    uploadedDate = beforeDate.toUploadedDate(LocalDate.now()),
                    beforeDate = beforeDate,
                    createdDt = LocalDateTime.now(),
                )
            }.orEmpty()
    }

    fun String.refineTitle(): String {
        return replace("\\[.*?\\]\\s?".toRegex(), "")
            .replace("_안양감리교회", "")
    }

    fun String.toUploadedDate(now: LocalDate): LocalDate {
        return runCatching {
            val time = "\\d+".toRegex().find(this)?.value?.toLong() ?: 0L
            val period = this.split(time.toString())[1]

            return when (period) {
                "시간 전" -> now
                "일 전" -> now.minusDays(time)
                "주 전" -> now.minusWeeks(time)
                "개월 전" -> now.minusMonths(time)
                else -> now
            }
        }.getOrDefault(now)
    }
}