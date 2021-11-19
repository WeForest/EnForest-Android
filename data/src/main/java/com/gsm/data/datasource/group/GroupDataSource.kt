package com.gsm.data.datasource.group

import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.BaseGroupResponse
import com.gsm.data.entity.group.response.CreateGroupResponse

interface GroupDataSource {
    suspend fun joinGroup(token: String, id: Int): BaseGroupResponse
    suspend fun secessionGroup(token: String, id: Int): BaseGroupResponse
    suspend fun createGroup(token:String,request:CreateGroupRequest): CreateGroupResponse
    suspend fun deleteGroup(token:String,id:Int): BaseGroupResponse

}