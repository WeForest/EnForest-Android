package com.gsm.data.entity.group.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(val name:String,val profileImg:String?) : Parcelable
