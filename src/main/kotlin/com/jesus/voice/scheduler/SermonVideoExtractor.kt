package com.jesus.voice.scheduler

import com.jesus.voice.aggregate.sermon.service.SermonExtractService
import com.jesus.voice.common.dtos.AYMCPlayList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SermonVideoExtractor(
    private val sermonExtractService: SermonExtractService,
) {
    /**
     * @see https://docs.spring.io/spring-framework/reference/integration/scheduling.html#scheduling-cron-expression
     */
    @Scheduled(cron = "0 0 1 * * 3")
    fun runScheduler() = listOf(
        AYMCPlayList.SUNDAY_1,
        AYMCPlayList.DADRIM,
        AYMCPlayList.SUNDAY,
    ).forEach { channel ->
        runBlocking {
            launch {
                sermonExtractService.extractSermon(channel, channel.id, 1)
                sermonExtractService.sleepRandomDuration()
            }
        }
    }
}

