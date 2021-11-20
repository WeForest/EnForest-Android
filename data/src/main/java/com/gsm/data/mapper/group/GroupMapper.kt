package com.gsm.data.mapper.group

import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.*
import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.*
import com.gsm.domain.entity.group.response.search.SearchGroupEntity

fun BaseGroupResponse.toDomain(): BaseGroupEntity {
    return BaseGroupEntity(this.message, this.code, this.success)
}

fun CreateGroup.toData(): CreateGroupRequest {
    return CreateGroupRequest(this.name, this.description, this.tags)
}

fun CreateGroupResponse.toDomain(): CreateGroupEntity {
    return CreateGroupEntity(this.success, this.group?.toDomain())
}

fun Group.toDomain(): GroupData {
    return GroupData(this.description, this.id, this.name, this.userId, this.tags)

}

fun SearchChatResponse.toDomain(): SearchChatEntity {
    return SearchChatEntity(
        this.chattingLog?.toDomain(),
        this.chattingParticipant?.toDomain(),
        this.id,
        this.name,
        this.type
    )

}

fun SearchGroupResponse.toDomain(): SearchGroupEntity {
    return SearchGroupEntity()

}

fun ChattingLog.toDomain(): ChattingLogData {
    return ChattingLogData()

}

fun ChattingParticipant.toDomain(): ChattingParticipantData {
    return ChattingParticipantData()

}