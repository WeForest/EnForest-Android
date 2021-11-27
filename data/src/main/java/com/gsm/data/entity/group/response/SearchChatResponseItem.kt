package com.gsm.data.entity.group.response


import com.google.gson.annotations.SerializedName

data class SearchChatResponseItem(
    @SerializedName("ChattingLog")
    val chattingLog: ChattingLogX?,
    @SerializedName("ChattingParticipant")
    val chattingParticipant: ChattingParticipantX?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?
)