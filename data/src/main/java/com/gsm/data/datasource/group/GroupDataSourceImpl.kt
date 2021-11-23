package com.gsm.data.datasource.group

import com.gsm.data.base.BaseDataSource
import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.*
import com.gsm.data.network.service.GroupService
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(override val service: GroupService) : BaseDataSource<GroupService>(),
    GroupDataSource {
    override suspend fun joinGroup(token: String, id: Int): JoinGroupResponse {

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

    override suspend fun searchChat(page: Int, k: String):SearchChatResponse {
        return service.searchChat(page,k)
    }

    override suspend fun searchGroup(page: Int, k: String): List<SearchGroupResponse> {
        return service.searchGroup(page,k)
    }
}