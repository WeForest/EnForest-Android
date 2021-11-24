package com.gsm.data.datasource.test

import com.google.gson.JsonArray
import com.gsm.data.base.BaseDataSource
import com.gsm.data.entity.test.response.GetTestItem
import com.gsm.data.entity.test.response.GetTestResponse
import com.gsm.data.mapper.test.toDomain
import com.gsm.data.network.service.TestService
import com.gsm.domain.entity.test.response.GetTestEntity
import retrofit2.Call
import javax.inject.Inject

class TestDataSourceImpl @Inject constructor(
    override val service: TestService
) : BaseDataSource<TestService>(), TestDataSource{

    override suspend fun getTest(): GetTestResponse {
        return service.viewTest()
    }


}