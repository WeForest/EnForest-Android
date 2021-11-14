package com.gsm.presentation.di

import com.gsm.data.datasource.mission.local.MissionLocalDataSource
import com.gsm.data.datasource.mission.local.MissionLocalDataSourceImpl
import com.gsm.data.datasource.mission.remote.MissionRemoteDataSource
import com.gsm.data.datasource.mission.remote.MissionRemoteDataSourceImpl
import com.gsm.data.db.mission.MissionDao
import com.gsm.data.network.service.MissionService
import com.gsm.domain.datasource.sign.LoginDataSourceImpl
import com.gsm.domain.network.service.sign.LoginService
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
    @Provides
    @Singleton
        fun provideLoginSource(authRemote: LoginService): LoginDataSourceImpl {
            return LoginDataSourceImpl(authRemote)
        }
    @Provides
    @Singleton
        fun provideUserDataSource(authRemote: MissionService): MissionLocalDataSource {
            return MissionLocalDataSourceImpl(authRemote)
        }


}


