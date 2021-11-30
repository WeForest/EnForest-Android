package com.gsm.presentation.bind

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gsm.presentation.R

object MissionBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:toInt")
    fun TextView.toInt(data: Int) {
        text = data.toString()
    }

    // 서버에서 가져온 이미지를 Gilde 라이브러리를통해 사이즈에 맞게 자른다
    @JvmStatic
    @BindingAdapter("app:imageLoad")
    fun ImageView.imageLoad(data: String?) {
        if(data?.isNotEmpty() == true) {
            Glide.with(this.context)
                .load(data)
                .override(130, 80)
                .fitCenter()
                .into(this)
        };
    }

    @JvmStatic
    @BindingAdapter("app:profileImage")
    fun ImageView.profileImage(data: String?) {
        if(data?.isNotEmpty() == true) {
            Glide.with(this.context)
                .load(data)
                .fitCenter()
                .into(this)
        };
    }
    @JvmStatic
    @BindingAdapter("app:chatImage")
    fun ImageView.chatImage(data: String?) {
        Log.d("TAG", "chatImage: ${data}")
            Glide.with(this.context)
                .load(data)
                .fitCenter()
                .error(R.drawable.profile)
                .fallback(R.drawable.profile)
                .override(40, 40)

                .into(this)
    }

    // 서버에서 가져온 이미지를 Gilde 라이브러리를통해 사이즈에 맞게 자른다
    @JvmStatic
    @BindingAdapter("app:profileString")
    fun TextView.profileData(data: String?) {
        if(data?.isEmpty() == true){
            this.text="정보를 입력해 주세요"
        }else{
            this.text=data
        }
    }




}