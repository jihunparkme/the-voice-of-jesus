package com.jesus.voice.youtube.dto

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import kotlin.test.Test

class VideoIdTest {
    @Test
    fun videoIdTest() {
        shouldNotThrow<IllegalArgumentException> {
            VideoId("2TEEsIdQ2nY")
        }

        shouldThrow<IllegalArgumentException> {
            VideoId("2TEEsdQ2nY")
        }
    }
}