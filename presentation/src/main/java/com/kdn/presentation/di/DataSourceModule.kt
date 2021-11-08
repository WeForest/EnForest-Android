package com.kdn.presentation.di

import com.gsm.data.datasource.mission.MissionDataSourceImpl
import com.gsm.data.network.service.MissionService
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
    fun provideUserDataSource(authRemote: MissionService): MissionDataSourceImpl {
        return MissionDataSourceImpl(authRemote)
    }

}
