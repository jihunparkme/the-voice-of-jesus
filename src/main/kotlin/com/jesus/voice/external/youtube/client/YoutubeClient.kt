package com.jesus.voice.external.youtube.client

import com.jesus.voice.common.dtos.ResponseResult
import com.jesus.voice.config.KtorClient
import com.jesus.voice.config.responseResult
import com.jesus.voice.external.youtube.dto.Const.YOUTUBE_PLAYLIST_URL
import com.jesus.voice.external.youtube.dto.Const.YOUTUBE_TRANSCRIPT_URL
import com.jesus.voice.external.youtube.dto.Const.YOUTUBE_WATCH_URL
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.springframework.stereotype.Component

@Component
class YoutubeClient(
    private val ktorClient: KtorClient,
) {
    fun getVideoPage(videoId: String): ResponseResult<String> =
        runBlocking {
            ktorClient.get(YOUTUBE_WATCH_URL + videoId)
                .responseResult<String>()
        }

    fun getTranscript(transcriptUrl: String): ResponseResult<String> =
        runBlocking {
            ktorClient.get(transcriptUrl)
                .responseResult<String>()
        }

    fun getTranscriptV2(transcriptParam: String): ResponseResult<TranscriptResponse> =
        runBlocking {
            ktorClient.post(YOUTUBE_TRANSCRIPT_URL, TranscriptRequestBody(params = transcriptParam))
                .responseResult<TranscriptResponse>()
        }

    fun getPlayList(playListId: String): ResponseResult<String> =
        runBlocking {
            ktorClient.get(YOUTUBE_PLAYLIST_URL + playListId)
                .responseResult<String>()
        }
}

/** TranscriptRequestBody */
@Serializable
data class TranscriptRequestBody(
    val context: RequestContext = RequestContext(),
    val params: String
)

@Serializable
data class RequestContext(
    val client: ClientInfo = ClientInfo()
)

@Serializable
data class ClientInfo(
    val clientName: String = "WEB",
    val clientVersion: String = "2.20250610.00.00"
)

/** TranscriptResponse */
@Serializable
data class TranscriptResponse(
    val actions: List<Action>? = null
)

@Serializable
data class Action(
    val clickTrackingParams: String? = null,
    val updateEngagementPanelAction: UpdateEngagementPanelAction? = null
)

@Serializable
data class UpdateEngagementPanelAction(
    val targetId: String? = null,
    val content: Content? = null
)

@Serializable
data class Content(
    val transcriptRenderer: TranscriptRenderer? = null
)

@Serializable
data class TranscriptRenderer(
    val trackingParams: String? = null,
    val content: TranscriptContent? = null // 필드 이름을 content로 변경 (JSON 구조에 맞춤)
)

@Serializable
data class TranscriptContent( // 새로운 클래스명 (기존 TranscriptRenderer의 content 필드에 해당)
    val transcriptSearchPanelRenderer: TranscriptSearchPanelRenderer? = null
)

@Serializable
data class TranscriptSearchPanelRenderer(
    val body: TranscriptBody? = null
)

@Serializable
data class TranscriptBody(
    val transcriptSegmentListRenderer: TranscriptSegmentListRenderer? = null
)

@Serializable
data class TranscriptSegmentListRenderer(
    val initialSegments: List<TranscriptSegmentWrapper>? = null // 클래스명 변경
)

@Serializable
data class TranscriptSegmentWrapper(
    val transcriptSegmentRenderer: TranscriptSegmentRenderer? = null
)

@Serializable
data class TranscriptSegmentRenderer(
    val startMs: String? = null,
    val endMs: String? = null,
    val snippet: Snippet? = null,
    val startTimeText: SimpleText? = null,
    val trackingParams: String? = null,
    val accessibility: AccessibilityContainer? = null, // 클래스명 변경
    val targetId: String? = null
)

@Serializable
data class Snippet(
    val runs: List<Run>? = null
)

@Serializable
data class Run(
    val text: String? = null
)

@Serializable
data class SimpleText(
    val simpleText: String? = null
)

@Serializable
data class AccessibilityContainer(
    val accessibilityData: AccessibilityData? = null
)

@Serializable
data class AccessibilityData(
    val label: String? = null
)

