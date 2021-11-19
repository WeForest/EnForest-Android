package com.gsm.data.datasource.group

import com.gsm.data.base.BaseDataSource
import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.BaseGroupResponse
import com.gsm.data.entity.group.response.CreateGroupResponse
import com.gsm.data.network.service.GroupService

class GroupDataSourceImpl(override val service: GroupService) : BaseDataSource<GroupService>(),
    GroupDataSource {
    override suspend fun joinGroup(token: String, id: Int): BaseGroupResponse {

        return service.joinGroup(token, id)
    }

    override suspend fun secessionGroup(token: String, id: Int): BaseGroupResponse {
        return service.secessionGroup(token, id)
    }

    override suspend fun createGroup(
        token: String,
        request: CreateGroupRequest
    ): CreateGroupResponse {
        return service.createGroup(token, request)
    }

    override suspend fun deleteGroup(token: String, id: Int): BaseGroupResponse {
        return service.deleteGroup(token, id)
    }
}