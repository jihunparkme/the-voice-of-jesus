package com.jesus.voice.test

import io.kotest.matchers.shouldBe
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.co.shineware.nlp.komoran.model.Token
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test

class KomoranTest {
    @Test
    fun success_komoran_light_analyze() {
        val komoran = Komoran(DEFAULT_MODEL.LIGHT)
        val (result, worldCount) = getMorphologicalAnalysisResult(komoran)
        print(result)
        worldCount.toList().get(0) shouldBe ("기도" to 146)
        worldCount.toList().get(1) shouldBe ("하나님" to 53)
        worldCount.toList().get(2) shouldBe ("말씀" to 28)
    }

    @Test
    fun success_komoran_full_analyze() {
        val komoran = Komoran(DEFAULT_MODEL.FULL)
        val (result, worldCount) = getMorphologicalAnalysisResult(komoran)
        print(result)
        worldCount.toList().get(0) shouldBe ("기도" to 147)
        worldCount.toList().get(1) shouldBe ("하나님" to 53)
        worldCount.toList().get(2) shouldBe ("때" to 28)
    }

    private fun getMorphologicalAnalysisResult(komoran: Komoran): Pair<String, Map<String, Int>> {
        val contents = getResourceContents()
        val analyzeResultList = komoran.analyze(contents)

        val tokenList: List<Token> = analyzeResultList.tokenList
        for (token in tokenList) {
            System.out.format(
                "(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos()
            )
        }

        val newLineIndexList = mutableListOf<Int>()
        tokenList
            .filter { token -> (token.getMorph() + token.getPos()).containsAny("다EC", "다EF") }
            .forEach { token ->
                newLineIndexList.add(token.getEndIndex())
            }
        val result = contents.insertNewlines(newLineIndexList)
        val worldCount: MutableMap<String, Int> = mutableMapOf()
        tokenList
            .filter { token -> list.contains(token.getPos()) }
            .forEach { token ->
                val morph = token.getMorph()
                worldCount[morph] = worldCount.getOrDefault(morph, 0) + 1
            }

        val sortedWorldCount = worldCount.entries
            .sortedByDescending { it.value }
            .associate { it.key to it.value }

        // sortedWorldCount.forEach { (key, value) ->
        //     println("$key: $value")
        // }

        return Pair(result, sortedWorldCount)
    }

    private fun getResourceContents(): String {
        val resource = javaClass.classLoader.getResource("transcript/transcript.txt")
        val contents = Files.readString(Paths.get(resource.toURI()))
        val replace = contents.replace("\n", " ")
        return replace
    }

}

val list = listOf("NNG", "NNP")

fun String.insertNewlines(positions: List<Int>): String {
    val builder = StringBuilder(this)
    val sortedPositions = positions.sorted()
    var offset = 0

    for (pos in sortedPositions) {
        require(pos + offset in 0..builder.length) { "Position $pos is out of range for the string." }
        builder.insert(pos + offset, "\n")
        offset++
    }

    return builder.toString()
}

fun String.containsAny(vararg keywords: String): Boolean {
    return keywords.any { this.contains(it) }
}