package com.gsm.presentation.di

import android.content.Context
import androidx.room.Room
import com.gsm.data.db.mission.MissionDataBase
import com.gsm.presentation.util.Constant.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MissionDataBase::class.java,
        DATABASE_NAME

    ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideDao(database: MissionDataBase) = database.missionDao()

}