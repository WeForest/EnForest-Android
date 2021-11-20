package com.gsm.data.repository

import com.gsm.data.datasource.group.GroupDataSourceImpl
import com.gsm.data.entity.group.response.SearchChatResponse
import com.gsm.data.mapper.group.toDomain
import com.gsm.data.mapper.group.toData
import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.BaseGroupEntity
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.domain.entity.group.response.SearchChatEntity
import com.gsm.domain.entity.group.response.search.SearchGroupEntity
import com.gsm.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSourceImpl: GroupDataSourceImpl
) : GroupRepository {
    override suspend fun joinGroup(token: String, id: Int): BaseGroupEntity {
        return groupDataSourceImpl.joinGroup(token, id).toDomain()
    }

    override suspend fun secessionGroup(token: String, id: Int): BaseGroupEntity {
        return groupDataSourceImpl.secessionGroup(token, id).toDomain()
    }

    override suspend fun createGroup(token: String, request: CreateGroup): CreateGroupEntity {
        return groupDataSourceImpl.createGroup(token, request.toData()).toDomain()
    }

    override suspend fun deleteGroup(token: String, id: Int): BaseGroupEntity {
        return groupDataSourceImpl.deleteGroup(token, id).toDomain()
    }

    override suspend fun searchChat(page: Int, key: String): SearchChatEntity {
        return groupDataSourceImpl.searchChat(page,key).toDomain()
    }

    override suspend fun searchGroup(page: Int, key: String): List<SearchGroupEntity> {
        return groupDataSourceImpl.searchGroup(page,key).map {
            it.toDomain()
        }

    }
}