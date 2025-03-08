package com.jesus.voice.common

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.UUID

class UUIDGenerator {

    @Test
    fun generateUUID() {
        val inputString = "generate-uuid"
        val uuid = UUID.nameUUIDFromBytes(inputString.toByteArray())
        uuid.toString() shouldBe "8a9b26f4-38f6-38cf-ac63-54028d6d6cf0"
    }
}