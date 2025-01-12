package com.jesus.voice.youtube.extractor

import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class TranscriptExtractor {
    companion object {
        fun extractTranscript(transcript: String): String =
            Jsoup.parse(transcript, "", Parser.xmlParser())
                .select("text")
                .joinToString("\n") { it.text() }
    }
}
