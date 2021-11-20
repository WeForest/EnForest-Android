package com.gsm.domain.entity.group.response


data class SearchChatEntity
    (
    val chattingLog: ChattingLogData?,
    val chattingParticipant: ChattingParticipantData?,
    val id: Int?,
    val name: String?,
    val type: String?
)