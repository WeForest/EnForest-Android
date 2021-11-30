package com.gsm.domain.entity.test.response

import com.google.gson.annotations.SerializedName

class GetMHTestEntity : ArrayList<GetTestItemData>()

data class GetMHTestItemData(
    @SerializedName("questions_num")
    val questionsNum: String,
    @SerializedName("questions")
    val questions: String,
    @SerializedName("one_num")
    val oneNum: String,
    @SerializedName("two_num")
    val twoNum: String,
    @SerializedName("three_num")
    val threeNum: String,
    @SerializedName("four_num")
    val fourNum: String,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("Commentary")
    val Commentary: String
)