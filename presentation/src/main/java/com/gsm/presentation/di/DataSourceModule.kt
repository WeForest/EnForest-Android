package com.gsm.presentation.di

import com.gsm.domain.datasource.sign.LoginDataSourceImpl
import com.gsm.domain.network.service.sign.LoginService
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
    fun provideLoginSource(authRemote: LoginService): LoginDataSourceImpl {
        return LoginDataSourceImpl(authRemote)
    }
    fun provideUserDataSource(authRemote: MissionService): MissionDataSourceImpl {
        return MissionDataSourceImpl(authRemote)
    }

}
   

