package com.jesus.voice.common.dtos

import com.jesus.voice.aggregate.sermon.domain.PlayList
import com.jesus.voice.common.dtos.ChannelType.AYMC
import com.jesus.voice.common.exception.NotFoundPlayListChannel

enum class ChannelType(
    val title: String,
    val url: String,
) {
    ALL("", ""),
    AYMC("안양감리교회", "https://www.aymc.org/"),
    ;

    companion object {
        fun from(value: String): ChannelType {
            return entries.firstOrNull { it.name == value } ?: ALL
        }

        fun channelList(): List<Pair<String, String>> =
            ChannelType.entries.map { Pair(it.name, it.title) }
    }
}

interface PlayListChannel {
    val title: String
    val id: String

    fun toDocument(): PlayList = PlayList(this.title, this.id)
}

// TODO: new play_list
fun getPlayList(channel: String): List<Pair<String, String>> {
    return when (ChannelType.from(channel)) {
        AYMC -> AYMCPlayList.titleList()
        else -> AYMCPlayList.titleList()
    }
}

enum class AYMCPlayList(
    override val title: String,
    override val id: String,
) : PlayListChannel {
    SUNDAY_1("주일1부예배", "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9"),
    DADRIM("다드림예배", "PLVK2VzE62knxZpbQVCg_4VtY-FA5X80Jl"),
    SUNDAY("안양감리교회 주일예배", "PLVK2VzE62knzUrtNYtjiuSI6UJO2fhFKA"),
    // DAWN("새벽기도회", "PLVK2VzE62knwgDZIzr-bxi_S6URleOU8C"), // 스크립트 미제공
    // FRIDAY_RECOVERY("금요회복기도회", "PLVK2VzE62knwvJiCH0yExJnUUibDQ1-D5"), // 스크립트 미제공
    ;

    companion object {
        fun from(value: String): String {
            return AYMCPlayList.entries.firstOrNull { it.name == value }?.title ?: ""
        }

        fun fromById(value: String): AYMCPlayList {
            return AYMCPlayList.entries.firstOrNull { it.id == value } ?: throw NotFoundPlayListChannel()
        }

        fun titleList(): List<Pair<String, String>> =
            entries.map { Pair(it.name, it.title) }
    }
}

// TODO: new play_list
val ALL_PLAYLIST = AYMCPlayList.entries