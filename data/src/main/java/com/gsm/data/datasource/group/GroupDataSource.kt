package com.gsm.data.datasource.group

import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.*

interface GroupDataSource {
    suspend fun joinGroup(token: String, id: Int): JoinGroupResponse
    suspend fun secessionGroup(token: String, id: Int): BaseResponse
    suspend fun createGroup(token:String,request:CreateGroupRequest): CreateGroupResponse
    suspend fun deleteGroup(token:String,id:Int): BaseResponse
    suspend fun searchChat(page:Int,k:String): SearchChatResponse
    suspend fun searchGroup(page:Int,k:String): List<SearchGroupResponse>

}