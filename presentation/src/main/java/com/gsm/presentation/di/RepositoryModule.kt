package com.gsm.presentation.di

import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.repository.ProfileRepositoryImpl
import com.gsm.domain.repository.ProfileRepository
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
    fun provideProfileRepository(profileDataSourceImpl: ProfileDataSourceImpl): ProfileRepository {
        return ProfileRepositoryImpl(profileDataSourceImpl)
    }

}