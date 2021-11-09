package com.gsm.presentation.di

import com.gsm.data.datasource.mission.MissionDataSourceImpl
import com.gsm.data.repository.MissionRepositoryImpl
import com.gsm.domain.repository.MissionRepository
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
    fun provideMissionRepository(missionDataSourceImpl: MissionDataSourceImpl): MissionRepository {
        return MissionRepositoryImpl(missionDataSourceImpl)
    }

}