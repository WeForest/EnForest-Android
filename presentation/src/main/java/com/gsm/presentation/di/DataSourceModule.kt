package com.gsm.presentation.di


import com.gsm.data.datasource.group.GroupDataSource
import com.gsm.data.datasource.group.GroupDataSourceImpl
import com.gsm.data.datasource.mission.local.MissionLocalDataSource
import com.gsm.data.datasource.mission.local.MissionLocalDataSourceImpl
import com.gsm.data.datasource.mission.remote.MissionRemoteDataSourceImpl
import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.datasource.sign.LoginDataSourceImpl
import com.gsm.data.datasource.test.TestDataSourceImpl
import com.gsm.data.db.mission.MissionDao
import com.gsm.data.network.service.GroupService
import com.gsm.data.network.service.MissionService
import com.gsm.data.network.service.ProfileService
import com.gsm.data.network.service.TestService
import com.gsm.data.network.service.sign.LoginService
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
    fun provideTestDataSource(authRemote: TestService): TestDataSourceImpl {
        return TestDataSourceImpl(authRemote)
    }

    @Provides
    @Singleton
    fun provideUserProfileDataSource(authRemote: ProfileService): ProfileDataSourceImpl {
        return ProfileDataSourceImpl(authRemote)
    }


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
    @Provides
    @Singleton
    fun provideGroupDataSource(authRemote: GroupService): GroupDataSource {
        return GroupDataSourceImpl(authRemote)

    }
}