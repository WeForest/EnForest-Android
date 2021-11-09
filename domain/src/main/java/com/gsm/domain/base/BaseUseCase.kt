package com.gsm.domain.base

abstract class BaseUseCase<out T> {
    abstract fun buildUseCaseObservable(): T
}