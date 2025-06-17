package com.jesus.voice.scheduler

import com.jesus.voice.common.IntegrationTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@Disabled
@IntegrationTest
class SermonVideoExtractorTest(
    @Autowired val sermonVideoExtractor: SermonVideoExtractor,
) {
    @Test
    fun extract_sermon_video() {
        sermonVideoExtractor.runScheduler()
    }
}