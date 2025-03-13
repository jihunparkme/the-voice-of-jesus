package com.jesus.voice.scheduler

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("beta")
@SpringBootTest
class SermonVideoExtractorProduct(
    @Autowired val sermonVideoExtractor: SermonVideoExtractor,
) {
    @Test
    fun extract_sermon_video() {
        sermonVideoExtractor.runScheduler()
    }
}