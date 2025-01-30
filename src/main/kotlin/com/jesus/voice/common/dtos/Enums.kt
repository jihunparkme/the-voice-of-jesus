package com.jesus.voice.common.dtos

import com.jesus.voice.aggregate.sermon.domain.PlayList

enum class ChannelType(
    val title: String,
    val url: String,
) {
    AYMC("안양감리교회", "https://www.aymc.org/")
}

enum class PlayListChannel(
    val title: String,
    val id: String,
) {
    SUNDAY_1("주일1부예배", "PLVK2VzE62knzVtluDggBd7UiwTiWS2DW9"),
    DADRIM("다드림예배", "PLVK2VzE62knxZpbQVCg_4VtY-FA5X80Jl"),
    DAWN("새벽기도회", "PLVK2VzE62knwgDZIzr-bxi_S6URleOU8C"),
    FRIDAY_RECOVERY("금요회복기도회", "PLVK2VzE62knwvJiCH0yExJnUUibDQ1-D5"),
    SUNDAY("안양감리교회 주일예배", "PLVK2VzE62knzUrtNYtjiuSI6UJO2fhFKA"),
    ;

    fun toDocument() =
        PlayList(this.title, this.id)
}
