package com.jesus.voice.transcript

import com.jesus.voice.youtube.extractor.TranscriptExtractor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.nio.file.Paths

class TranscriptExtractorTest : BehaviorSpec({
    Given("transcript xml 컨텐츠가 전달되면") {
        val resource = javaClass.classLoader.getResource("transcript/transcript.xml")
        val content = Files.readString(Paths.get(resource.toURI()))

        When(" transcript 추출") {
            val result = TranscriptExtractor.extractTranscript(content)
            result shouldBe """
                [Music]
                start uhhuh
                [Music]
                uhuh uh uhhuh uh-huh kissy face kissy
                face sent to your phone butt I'm trying
                to kiss your Li for real uh-huh uh-huh
                red hearts red hearts that's what I'm on
                yeah come get me something I can feel oh
                oh oh don't you want me like I want you
                baby don't you need me like I need you
                now sleep tomorrow but tonight go crazy
                all you got to do is just meet me at the
                [Music]
                I uhuh
                [Music]
                uh-huh uhh uhh it's whatever it's
                whatever it's what but you like turn
                this OPP into a club I'm talking drink
                dance smoke freak party all night come
                on gum gum what's up oh oh oh don't you
                want me like I want you baby don't you
                need me like I need you now sleep
                tomorrow but tonight go crazy all you
                got to do is just meet me at the
                I I it I it uh uh-huh uh-huh I it I it I
                it I it I it I it uh uh-huh uh-huh hey
                so now you know the game are you ready
                cuz I'm coming to get you get you get
                you hold on hold on I'm on my
                way yeah yeah yeah yeah yeah I'm on my
                way hold on hold on I'm on my way
                yeah yeah yeah yeah yeah yeah I'm on my
                way don't you want me like I want you
                baby don't you need me like I need you
                now sleep tomorrow but tonight go crazy
                all you got to do is just meet me at
                I
                I I
                I that's it
                IET me I it
                I
                I make me
            """.trimIndent()
        }
    }
})