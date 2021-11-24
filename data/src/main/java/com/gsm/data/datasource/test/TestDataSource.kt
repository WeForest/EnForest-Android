package com.gsm.data.datasource.test

import com.google.gson.JsonArray
import com.gsm.data.entity.test.response.GetTestItem
import com.gsm.data.entity.test.response.GetTestResponse
import com.gsm.domain.entity.test.response.GetTestEntity
import retrofit2.Call

interface TestDataSource {
    suspend fun getTest(): GetTestResponse
}