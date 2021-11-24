package com.gsm.presentation.di

import com.gsm.data.datasource.test.TestDataSourceImpl
import com.gsm.data.network.service.TestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideTestDataSource(authRemote: TestService): TestDataSourceImpl {
        return TestDataSourceImpl(authRemote)
    }
}