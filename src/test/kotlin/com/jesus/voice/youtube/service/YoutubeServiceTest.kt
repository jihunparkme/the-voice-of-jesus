package com.jesus.voice.youtube.service

import com.jesus.voice.common.IntegrationTest
import com.jesus.voice.external.youtube.dto.VideoId
import com.jesus.voice.external.youtube.service.YoutubeService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

@IntegrationTest
class YoutubeServiceTest(
    private val youtubeService: YoutubeService,
) : BehaviorSpec({

    given("비디오 아이디를 전달해서 자막을 요청할 경우") {
        When("자막이 있는 영상이라면") {
            Then("자막이 추출된다").config(enabled = false) {
                val videoId = "ekr2nIex040"
                val result = youtubeService.getTranscript(VideoId(videoId))

                result shouldBe """
                    [Music]
                    start uhhuh
                    [Music]
                    uhuh uh uhhuh uh-huh kissy face kissy
                    face sent to your phone butt I&#39;m trying
                    to kiss your Li for real uh-huh uh-huh
                    red hearts red hearts that&#39;s what I&#39;m on
                    yeah come get me something I can feel oh
                    oh oh don&#39;t you want me like I want you
                    baby don&#39;t you need me like I need you
                    now sleep tomorrow but tonight go crazy
                    all you got to do is just meet me at the
                    [Music]
                    I uhuh
                    [Music]
                    uh-huh uhh uhh it&#39;s whatever it&#39;s
                    whatever it&#39;s what but you like turn
                    this OPP into a club I&#39;m talking drink
                    dance smoke freak party all night come
                    on gum gum what&#39;s up oh oh oh don&#39;t you
                    want me like I want you baby don&#39;t you
                    need me like I need you now sleep
                    tomorrow but tonight go crazy all you
                    got to do is just meet me at the
                    I I it I it uh uh-huh uh-huh I it I it I
                    it I it I it I it uh uh-huh uh-huh hey
                    so now you know the game are you ready
                    cuz I&#39;m coming to get you get you get
                    you hold on hold on I&#39;m on my
                    way yeah yeah yeah yeah yeah I&#39;m on my
                    way hold on hold on I&#39;m on my way
                    yeah yeah yeah yeah yeah yeah I&#39;m on my
                    way don&#39;t you want me like I want you
                    baby don&#39;t you need me like I need you
                    now sleep tomorrow but tonight go crazy
                    all you got to do is just meet me at
                    I
                    I I
                    I that&#39;s it
                    IET me I it
                    I
                    I make me
                """.trimIndent()
            }
        }
    }

    given("재생목록을 전달해서 동영상 목록을 요청할 경우") {
        When("동영상이 있는 재생목록이라면") {
            Then("재생목록이 추출된다.").config(enabled = false) {
                val playListId = "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9"
                val playListVideos = youtubeService.getVideoIdFromPlayList(playListId)

                playListVideos.size shouldBeGreaterThan 10
            }
        }
    }
})
