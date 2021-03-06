package com.gsm.data.db.mission

import androidx.room.*
import androidx.room.Insert
import com.gsm.data.entity.mission.response.GetMissionResponse

@Dao
interface MissionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(mission: GetMissionResponse)


    @Delete
    suspend fun deleteMission(mission: GetMissionResponse)


    @Query("SELECT * FROM mission_table WHERE type like :type and level like :level ")
    fun getMission(type: String, level: String): List<GetMissionResponse>


}