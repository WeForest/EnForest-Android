package com.gsm.presentation.di

import com.gsm.data.datasource.test.TestDataSourceImpl
import com.gsm.data.repositoty.TestRepositoryImpl
import com.gsm.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTestRepository(testSourceImpltestSourceImpl: TestDataSourceImpl): TestRepository {
        return TestRepositoryImpl(testSourceImpltestSourceImpl)
    }
}