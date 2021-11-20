package com.gsm.domain.usecase.group

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.repository.GroupRepository
import javax.inject.Inject

class SearchChatGroupUseCase @Inject constructor(private val repository: GroupRepository) :
    ParamsUseCase<SearchChatGroupUseCase.Params, Unit>() {
    data class Params(
        val page: Int,
        val k: String
    )

    override suspend fun buildUseCaseObservable(params: Params) {
        repository.searchChat(page = params.page, params.k)
    }
}