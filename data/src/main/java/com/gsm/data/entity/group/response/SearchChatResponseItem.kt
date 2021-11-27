package com.gsm.data.entity.group.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable