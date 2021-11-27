package com.gsm.data.entity.group.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class CreateGroupResponse(
    @SerializedName("group")
    val group: Group?,
    @SerializedName("success")
    val success: Boolean?
) : Parcelable