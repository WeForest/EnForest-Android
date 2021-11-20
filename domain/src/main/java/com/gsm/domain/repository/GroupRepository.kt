package com.gsm.domain.repository

import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.BaseGroupEntity
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.domain.entity.group.response.SearchChatEntity
import com.gsm.domain.entity.group.response.search.SearchGroupEntity

interface  GroupRepository {
    suspend fun joinGroup(token: String, id: Int): BaseGroupEntity
    suspend fun secessionGroup(token: String, id: Int): BaseGroupEntity
    suspend fun createGroup(token:String,request: CreateGroup): CreateGroupEntity
    suspend fun deleteGroup(token:String,id:Int): BaseGroupEntity
    suspend fun searchChat(page:Int,key:String):SearchChatEntity
    suspend fun searchGroup(page:Int,key:String):List<SearchGroupEntity>

}