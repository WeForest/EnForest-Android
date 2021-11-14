package com.gsm.data.db.mission

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gsm.data.entity.mission.response.GetMissionResponse

class MissionTypeConverter {
        var gson = Gson()


        @TypeConverter
        fun stringToResult(data: String): GetMissionResponse {
            val listType = object : TypeToken<GetMissionResponse>() {}.type
            return gson.fromJson(data, listType)
        }


        @TypeConverter
        fun resultToString(result: GetMissionResponse): String {
            return gson.toJson(result)
        }




}