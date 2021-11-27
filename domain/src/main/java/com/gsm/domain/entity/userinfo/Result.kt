package com.gsm.domain.entity.userinfo


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("accessToken")
    val accessToken: Any,
    @SerializedName("authCompany")
    val authCompany: Any,
    @SerializedName("companyEmail")
    val companyEmail: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("followers")
    val followers: List<Follower>,
    @SerializedName("following")
    val following: List<Following>,
    @SerializedName("group")
    val group: List<Group>,
    @SerializedName("interested")
    val interested: List<Interested>,
    @SerializedName("isJobSeeker")
    val isJobSeeker: Boolean,
    @SerializedName("level")
    val level: Int,
    @SerializedName("major")
    val major: List<Major>,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImg")
    val profileImg: Any,
    @SerializedName("purpose")
    val purpose: String
)