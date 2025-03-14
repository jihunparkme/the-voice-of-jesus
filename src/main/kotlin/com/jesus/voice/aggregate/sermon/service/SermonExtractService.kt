package com.jesus.voice.aggregate.sermon.service

import com.jesus.voice.aggregate.sermon.domain.Sermon
import com.jesus.voice.aggregate.sermon.domain.SermonRepository
import com.jesus.voice.common.dtos.ALL_PLAYLIST
import com.jesus.voice.common.dtos.PlayListChannel
import com.jesus.voice.common.util.logger
import com.jesus.voice.external.komoran.MorphemeAnalyzer
import com.jesus.voice.external.openai.gemini.service.GeminiChatService
import com.jesus.voice.external.youtube.dto.PlayListVideo
import com.jesus.voice.external.youtube.dto.VideoId
import com.jesus.voice.external.youtube.service.YoutubeService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SermonExtractService(
    @Value("\${app.random.start}") val randomStart: Long,
    @Value("\${app.random.end}") val randomEnd: Long,
    private val youtubeService: YoutubeService,
    private val sermonRepository: SermonRepository,
    private val geminiChatService: GeminiChatService,
) {

    fun extractSermon(channel: PlayListChannel, channelId: String, count: Int) {
        youtubeService.getVideoIdFromPlayList(channelId)
            .take(count)
            .filterNot { sermonRepository.existsByVideoId(it.videoId) }
            .map { generateSermon(it, channel) }
            .forEach { sermon ->
                sermonRepository.save(sermon)
                log.info("✅✅✅ Sermon saved: $sermon")
            }
    }

    fun extractSermonSchedule(count: Int) {
        ALL_PLAYLIST.forEach { channel ->
            runBlocking {
                launch {
                    extractSermon(channel, channel.id, count)
                    sleepRandomDuration()
                }
            }
        }
    }

    suspend fun sleepRandomDuration() {
        val randomDuration = (randomStart..randomEnd).random()
        log.info("🌙🌙🌙 Sleeping for $randomDuration ms...")
        delay(randomDuration)
    }

    private fun generateSermon(playListVideo: PlayListVideo, channel: PlayListChannel): Sermon {
        val transcript = runCatching {
            youtubeService.getTranscript(VideoId(playListVideo.videoId))
        }.getOrDefault("")
        if (transcript.isBlank()) {
            return playListVideo.toSermon(
                playList = channel.toDocument()
            )
        }
        val (refinedContent, wordCount) = MorphemeAnalyzer.analyze(transcript)
        val summarizedContent = geminiChatService.chat(CHAT_PREFIX + refinedContent)
        return playListVideo.toSermon(
            playList = channel.toDocument(),
            transcript = refinedContent,
            summary = summarizedContent,
            wordCount = wordCount,
        )
    }

    companion object {
        private val log by logger()
        private val CHAT_PREFIX = "교회 설교문을 요약해 주세요\n" +
            "\n" +
            "요약은 설교를 듣지 못 했거나, 설교 내용을 다시 보고 싶은 사람들을 위해 제공될 예정입니다.\n" +
            "\n" +
            "전체 내용을 핵심 주제와 몇 가지 키워드를 중점으로 요약해 주세요\n" +
            "\n" +
            "---\n" +
            "\n"
    }
}