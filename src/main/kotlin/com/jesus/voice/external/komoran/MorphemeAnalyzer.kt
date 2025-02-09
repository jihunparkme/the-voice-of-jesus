package com.jesus.voice.external.komoran

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.co.shineware.nlp.komoran.model.Token

object MorphemeAnalyzer {
    private val komoran = Komoran(DEFAULT_MODEL.LIGHT)
    private val NOUNS = setOf("NNG", "NNP")
    private val ENDINGS = setOf("다EC", "다EF")

    fun analyze(content: String): Pair<RefinedContent, WordCount> {
        val replacedContent = content.replace("\n", " ")

        val analysisResult = komoran.analyze(replacedContent)
        val tokenList = analysisResult.tokenList

        val refinedContent = applyNewLine(tokenList, replacedContent)
                                .applyParagraph()
                                .removeWhiteList()
        val wordCount = getWordCount(tokenList)

        return Pair(refinedContent, wordCount)
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
        replacedContent: String
    ): String = replacedContent.insertNewlines(
        tokenList
            .filter { "${it.getMorph()}${it.getPos()}}".containsAny() }
            .map { it.getEndIndex() }
    )

    private fun String.applyParagraph(): String =
        split("\n").map { it.trim() }
            .chunked(5)
            .joinToString("\n\n") { it.joinToString("\n") }

    private fun String.removeWhiteList(): String =
        replace(WHITE_LIST.joinToString("|") { Regex.escape(it) }.toRegex(), "")

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

typealias RefinedContent = String
typealias WordCount = Map<String, Int>
val WHITE_LIST: List<String> = listOf("[음악] ")