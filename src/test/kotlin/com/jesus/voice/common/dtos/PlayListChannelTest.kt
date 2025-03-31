package com.jesus.voice.common.dtos

import kotlin.test.Test

class PlayListChannelTest {
    @Test
    fun aa() {
        val playList = APlayList.entries + BPlayList.entries + CPlayList.entries
        playList.forEach { channel -> println(channel.id) }
        println(playList.find { channel -> channel.name == "BBB" }?.title)
    }
}

enum class APlayList(
    override val title: String,
    override val id: String,
) : PlayListChannel {
    AAA("A", "aaa"),
    ;
}

enum class BPlayList(
    override val title: String,
    override val id: String,
) : PlayListChannel {
    BBB("B", "bbb"),
    ;
}

enum class CPlayList(
    override val title: String,
    override val id: String,
) : PlayListChannel {
    CCC("C", "ccc"),
    ;
}