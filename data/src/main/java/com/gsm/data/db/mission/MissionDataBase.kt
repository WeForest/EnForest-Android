package com.gsm.data.db.mission

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gsm.data.entity.mission.response.GetMissionResponse
import com.gsm.domain.entity.mission.GetMissionTypePageEntity


@Database(
    entities = [GetMissionResponse::class],
    version = 1,
    exportSchema = false

)

@TypeConverters(MissionTypeConverter::class)
abstract class MissionDataBase : RoomDatabase() {
    abstract fun missionDao(): MissionDao

}