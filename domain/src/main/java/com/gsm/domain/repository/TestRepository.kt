package com.gsm.domain.repository

import com.google.gson.JsonArray
import com.gsm.domain.entity.test.response.GetTestEntity
import retrofit2.Call

interface TestRepository {

    suspend fun getTest() : GetTestEntity
}