package com.jesus.voice.test

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.co.shineware.nlp.komoran.model.Token
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test

class KomoranTest {
    @Test
    fun success_komoran_analyze() {
        val komoran = Komoran(DEFAULT_MODEL.LIGHT)

        val resource = javaClass.classLoader.getResource("transcript/transcript.txt")
        val strToAnalyze = Files.readString(Paths.get(resource.toURI()))
        val analyzeResultList = komoran.analyze(strToAnalyze)
        // println(analyzeResultList.plainText)

        val worldCount: MutableMap<String, Int> = mutableMapOf()
        val tokenList: List<Token> = analyzeResultList.tokenList
        // for (token in tokenList) {
        //     System.out.format(
        //         "%s/%s\n",
        //         token.getMorph(),
        //         token.getPos()
        //     )
        // }
        tokenList
            .filter { token -> list.contains(token.getPos()) }
            .forEach { token ->
                val morph = token.getMorph()
                worldCount[morph] = worldCount.getOrDefault(morph, 0) + 1
            }

        val sortedWorldCount = worldCount.entries
            .sortedByDescending { it.value }
            .associate { it.key to it.value }

        sortedWorldCount.forEach { (key, value) ->
            println("$key: $value")
        }
    }

    val list = listOf("NNG", "NNP")
}