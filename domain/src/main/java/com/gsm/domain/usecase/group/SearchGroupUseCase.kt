package com.gsm.domain.usecase.group

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.response.search.SearchGroupEntity
import com.gsm.domain.repository.GroupRepository
import javax.inject.Inject

class SearchGroupUseCase @Inject constructor(private val repository: GroupRepository) :
    ParamsUseCase<SearchGroupUseCase.Params, List<SearchGroupEntity>>() {
    data class Params(
        val page: Int,
        val k: String
    )

    override suspend fun buildUseCaseObservable(params: Params):List<SearchGroupEntity> {
      return  repository.searchGroup(page = params.page, params.k)
    }

}