package com.gsm.data.mapper.group

import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.BaseGroupResponse
import com.gsm.data.entity.group.response.CreateGroupResponse
import com.gsm.data.entity.group.response.Group
import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.BaseGroupEntity
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.domain.entity.group.response.GroupData

fun BaseGroupResponse.toDomain(): BaseGroupEntity {
    return BaseGroupEntity(this.message, this.code, this.success)
}

fun CreateGroup.toData(): CreateGroupRequest {
    return CreateGroupRequest(this.name,this.description,this.tags)
}

fun CreateGroupResponse.toDomain():CreateGroupEntity{
    return CreateGroupEntity(this.success,this.group?.toDomain())
}

fun Group.toDomain():GroupData {
    return GroupData(this.description,this.id,this.name,this.ownerId,this.tags)

}