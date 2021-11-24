package com.gsm.domain.base

abstract class BaseUseCase<out T> {
    abstract suspend fun buildUseCaseObservable(): T
}