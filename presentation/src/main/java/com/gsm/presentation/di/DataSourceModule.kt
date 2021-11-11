package com.gsm.presentation.di

import com.gsm.data.datasource.mission.local.MissionLocalDataSourceImpl
import com.gsm.data.datasource.mission.remote.MissionRemoteDataSourceImpl
import com.gsm.data.db.mission.MissionDao
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
    fun provideMissionLocalDataSource(authRemote: MissionService): MissionLocalDataSourceImpl {
        return MissionLocalDataSourceImpl(authRemote)
    }

    @Provides
    @Singleton
    fun provideMissionDataSource(dao: MissionDao): MissionRemoteDataSourceImpl {
        return MissionRemoteDataSourceImpl(dao)
    }

}
