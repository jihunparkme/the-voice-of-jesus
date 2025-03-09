package com.jesus.voice.scheduler

import com.jesus.voice.aggregate.sermon.service.SermonExtractService
import com.jesus.voice.common.dtos.AYMCPlayList
import com.jesus.voice.common.util.logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SermonVideoExtractor(
    private val sermonExtractService: SermonExtractService,
    @Value("\${app.random.start}") val randomStart: Long,
    @Value("\${app.random.end}") val randomEnd: Long,
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
                sleepRandomDuration()
            }
        }
    }

    private suspend fun sleepRandomDuration() {
        val randomDuration = (randomStart..randomEnd).random()
        log.info("🌙🌙🌙 Sleeping for $randomDuration ms...")
        delay(randomDuration)
    }

    companion object {
        private val log by logger()
    }
}

