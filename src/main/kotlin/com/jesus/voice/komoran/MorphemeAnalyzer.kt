package com.jesus.voice.komoran

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.co.shineware.nlp.komoran.model.Token

object MorphemeAnalyzer {
    private val komoran = Komoran(DEFAULT_MODEL.LIGHT)
    private val NOUNS = setOf("NNG", "NNP")
    private val ENDINGS = setOf("다EC", "다EF")

    fun analyze(contents: String): Pair<String, WordCount> {
        val replacedContents = contents.replace("\n", " ")

        val analysisResults = komoran.analyze(replacedContents)
        val tokenList = analysisResults.tokenList

        val refinedContents = applyNewLine(tokenList, replacedContents)
        val wordCount = getWordCount(tokenList)

        return Pair(refinedContents, wordCount)
    }

    private fun getWordCount(tokenList: List<Token>): Map<String, Int> =
        tokenList
            .filter { NOUNS.contains(it.getPos()) }
            .groupingBy { it.getMorph() }
            .eachCount().entries
            .sortedByDescending { it.value }
            .associate { it.key to it.value }
            .filterValues { it > 2 }

    private fun applyNewLine(
        tokenList: List<Token>,
        replacedContents: String
    ): String = replacedContents.insertNewlines(
        tokenList
            .filter { "${it.getMorph()}${it.getPos()}}".containsAny() }
            .map { it.getEndIndex() }
    )

    private fun String.insertNewlines(positions: List<Int>): String =
        buildString {
            var offset = 0
            val originalBuilder = StringBuilder(this@insertNewlines)
            positions.forEach { pos ->
                require(pos + offset in 0..originalBuilder.length) { "Position $pos is out of range for the string." }
                originalBuilder.insert(pos + offset, "\n")
                offset++
            }
            append(originalBuilder)
        }

    private fun String.containsAny(): Boolean =
        ENDINGS.any { this.contains(it) }
}

typealias WordCount = Map<String, Int>