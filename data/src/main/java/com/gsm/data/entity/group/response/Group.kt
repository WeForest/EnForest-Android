package com.gsm.data.entity.group.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName

import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ownerId")
    val userId: Int?,
    @SerializedName("tags")
    val tags: String?
) : Parcelable