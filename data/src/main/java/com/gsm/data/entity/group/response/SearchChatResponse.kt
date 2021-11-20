package com.gsm.data.entity.group.response


import com.google.gson.annotations.SerializedName

data class SearchChatResponse(
    @SerializedName("ChattingLog")
    val chattingLog: ChattingLog?,
    @SerializedName("ChattingParticipant")
    val chattingParticipant: ChattingParticipant?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?
)