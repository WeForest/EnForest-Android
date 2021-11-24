package com.gsm.domain.usecase.test

import com.gsm.domain.base.BaseUseCase
import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.domain.repository.TestRepository
import retrofit2.Call
import javax.inject.Inject

class GetTestUseCase @Inject constructor(private val repository: TestRepository) :
    BaseUseCase<GetTestEntity>() {

    override suspend fun buildUseCaseObservable(): GetTestEntity {
       return repository.getTest()
    }

}