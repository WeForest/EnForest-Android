package com.gsm.presentation.di

import com.gsm.data.datasource.mission.local.MissionLocalDataSourceImpl
import com.gsm.data.datasource.mission.remote.MissionRemoteDataSourceImpl
import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.datasource.sign.LoginDataSourceImpl
import com.gsm.data.repository.MissionLocalRepositoryImpl
import com.gsm.data.repository.MissionRemoteRepositoryImpl
import com.gsm.data.repository.ProfileRepositoryImpl
import com.gsm.data.repository.sign.LoginRepositoryImpl
import com.gsm.domain.repository.LoginRepository
import com.gsm.domain.repository.MissionRemoteRepository
import com.gsm.domain.repository.MissionRepository
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


    @Provides
    @Singleton
    fun provideLoginRepository(loginDataSourceImpl: LoginDataSourceImpl): LoginRepository {
        return LoginRepositoryImpl(loginDataSourceImpl)
    }

    @Provides
    @Singleton
    fun provideMissionRepository(missionDataSourceImpl: MissionLocalDataSourceImpl): MissionRepository {
        return MissionLocalRepositoryImpl(missionDataSourceImpl)
    }

    @Provides
    @Singleton
    fun provideMissionRemoteRepository(missionRemoteDataSourceImpl: MissionRemoteDataSourceImpl): MissionRemoteRepository {
        return MissionRemoteRepositoryImpl(missionRemoteDataSourceImpl)
    }

}