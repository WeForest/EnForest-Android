package com.gsm.domain.repository

import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.BaseEntity
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.domain.entity.group.response.JoinGroupEntity
import com.gsm.domain.entity.group.response.SearchChatEntity
import com.gsm.domain.entity.group.response.search.SearchGroupEntity

interface  GroupRepository {
    suspend fun joinGroup(token: String, id: Int): JoinGroupEntity
    suspend fun secessionGroup(token: String, id: Int): BaseEntity
    suspend fun createGroup(token:String,request: CreateGroup): CreateGroupEntity
    suspend fun deleteGroup(token:String,id:Int): BaseEntity
    suspend fun searchChat(page:Int,key:String):SearchChatEntity
    suspend fun searchGroup(page:Int,key:String):List<SearchGroupEntity>

}