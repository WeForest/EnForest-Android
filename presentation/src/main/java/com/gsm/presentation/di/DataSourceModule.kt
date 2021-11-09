package com.gsm.presentation.di

import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.network.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideUserProfileDataSource(authRemote: ProfileService): ProfileDataSourceImpl {
        return ProfileDataSourceImpl(authRemote)
    }

}