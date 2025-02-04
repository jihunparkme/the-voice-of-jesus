package com.jesus.voice.external.komoran

import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test

class MorphemeAnalyzerTest {
    @Test
    fun success_analyze() {
        val resource = javaClass.classLoader.getResource("transcript/transcript.txt")
        val contents = Files.readString(Paths.get(resource.toURI()))

        val (refinedContents, wordCount) = MorphemeAnalyzer.analyze(contents)

        println(refinedContents)
        println(wordCount)
        wordCount.toList().get(0) shouldBe ("기도" to 146)
        wordCount.toList().get(1) shouldBe ("하나님" to 53)
        wordCount.toList().get(2) shouldBe ("말씀" to 28)
    }
}