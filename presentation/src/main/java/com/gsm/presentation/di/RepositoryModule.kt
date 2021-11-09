package com.gsm.presentation.di

import com.gsm.domain.datasource.sign.LoginDataSourceImpl
import com.gsm.domain.repository.LoginRepository
import com.gsm.domain.repository.sign.LoginRepositoryImpl
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
    fun provideLoginRepository(loginDataSourceImpl: LoginDataSourceImpl): LoginRepository {
        return LoginRepositoryImpl(loginDataSourceImpl)
    }

    @Provides
    @Singleton
    fun provideMissionRepository(missionDataSourceImpl: MissionDataSourceImpl): MissionRepository {
        return MissionRepositoryImpl(missionDataSourceImpl)
    }

}