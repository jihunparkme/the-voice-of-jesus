package com.jesus.voice.aggregate.sermon.service

import com.jesus.voice.aggregate.sermon.domain.Sermon
import com.jesus.voice.aggregate.sermon.domain.SermonRepository
import com.jesus.voice.common.dtos.PlayListChannel
import com.jesus.voice.common.util.logger
import com.jesus.voice.external.komoran.MorphemeAnalyzer
import com.jesus.voice.external.openai.gemini.service.GeminiChatService
import com.jesus.voice.external.youtube.dto.PlayListVideo
import com.jesus.voice.external.youtube.dto.VideoId
import com.jesus.voice.external.youtube.service.YoutubeService
import org.springframework.stereotype.Service

@Service
class SermonExtractService(
    private val youtubeService: YoutubeService,
    private val sermonRepository: SermonRepository,
    private val geminiChatService: GeminiChatService,
) {

    fun extractSermon(channel: PlayListChannel, channelId: String, count: Int) {
        youtubeService.getVideoIdFromPlayList(channelId)
            .take(count)
            .filterNot { sermonRepository.existsByVideoId(it.videoId) }
            .map { generateSermon(it, channel) } // TODO: 채널 검색 -> Extractor 를 서비스 호출로 수정
            .forEach { sermon ->
                sermonRepository.save(sermon)
                log.info("✅✅✅ Sermon saved: $sermon")
            }
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
        private val CHAT_PREFIX = "아래 글은 교회의 설교 내용입니다. 내용을 요약해 주세요.\n\n"
    }
}