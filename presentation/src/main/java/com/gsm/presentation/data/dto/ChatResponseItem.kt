package com.gsm.presentation.data.dto



data class ChatResponseItem(
    val chattingId: Int?,
    val id: Int?,
    val content:String?,
    val abuse:Boolean?,
    val user: User?,
    val userId: Int?
)