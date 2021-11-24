package com.gsm.presentation.di


import com.gsm.data.datasource.group.GroupDataSourceImpl
import com.gsm.data.datasource.mission.local.MissionLocalDataSourceImpl
import com.gsm.data.datasource.mission.remote.MissionRemoteDataSourceImpl
import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.datasource.sign.LoginDataSourceImpl
import com.gsm.data.datasource.test.TestDataSourceImpl
import com.gsm.data.repository.GroupRepositoryImpl
import com.gsm.data.repository.MissionLocalRepositoryImpl
import com.gsm.data.repository.MissionRemoteRepositoryImpl
import com.gsm.data.repository.ProfileRepositoryImpl
import com.gsm.data.repository.sign.LoginRepositoryImpl
import com.gsm.data.repositoty.TestRepositoryImpl
import com.gsm.domain.repository.*
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
    @Provides
    @Singleton
    fun provideGroupRepository(groupDataSourceImpl: GroupDataSourceImpl): GroupRepository {
        return GroupRepositoryImpl(groupDataSourceImpl)

    }
    @Provides
    @Singleton
    fun provideTestRepository(testSourceImpltestSourceImpl: TestDataSourceImpl): TestRepository {
        return TestRepositoryImpl(testSourceImpltestSourceImpl)
    }
}