package com.gsm.data.repositoty

import android.util.Log
import com.gsm.data.datasource.test.TestDataSourceImpl
import com.gsm.data.mapper.test.toDomain
import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.domain.repository.TestRepository
import retrofit2.Call
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val dataSource: TestDataSourceImpl
) : TestRepository {

    override suspend fun getTest(): GetTestEntity {
        return dataSource.getTest().toDomain()
    }

}