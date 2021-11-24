package com.gsm.domain.base

abstract class ParamsUseCase<in Params, out T> {
    abstract suspend fun buildUseCaseObservable(params: Params): T
}