package com.gsm.data.entity.group.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class SearchGroupResponseItem(
    @SerializedName("chattingId")
    val chattingId: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("owner")
    val owner:Owner
): Parcelable