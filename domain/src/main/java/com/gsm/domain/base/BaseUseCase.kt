package com.gsm.domain.base

import com.gsm.domain.entity.test.response.GetTestEntity

abstract class BaseUseCase<out T> {
    abstract suspend fun buildUseCaseObservable(): T
}