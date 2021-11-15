package com.gsm.presentation.di

import com.gsm.domain.sign.LoginDataSourceImpl
import com.gsm.domain.sign.LoginService
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

        @Provides
        @Singleton
        fun provideLoginSource(authRemote: LoginService): LoginDataSourceImpl {
            return LoginDataSourceImpl(authRemote)
        }
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



