package com.gsm.presentation.di

import com.gsm.data.datasource.sign.LoginDataSourceImpl
import com.gsm.domain.repository.LoginRepository
import com.gsm.data.repository.sign.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideMissionRepository(loginDataSourceImpl: LoginDataSourceImpl): LoginRepository {
        return LoginRepositoryImpl(loginDataSourceImpl)
    }

}