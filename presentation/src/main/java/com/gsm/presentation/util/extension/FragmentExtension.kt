package com.gsm.presentation.util.extension

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsm.data.entity.profile.request.Major
import com.gsm.domain.entity.request.profile.InterestsItem
import com.gsm.domain.entity.request.profile.MajorItem
import com.gsm.presentation.R
import com.gsm.presentation.data.dto.ChatResponse
import com.gsm.presentation.ui.chat.ChatModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun RecyclerView.showVertical(context: Context) {
    this.layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun EditText.addTextChangedListener(context: Context, button: Button) {


    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {


        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.d("text", "onTextChanged: ${s?.length}")
            if (s?.length == 0) {

                button.apply {
                    background = (ContextCompat.getDrawable(context, R.drawable.sign_in_fail))
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                    isClickable = false
                }
            } else {
                button.apply {
                    background = (ContextCompat.getDrawable(context, R.drawable.sign_in_success))
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    isClickable = true
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    })


}

fun Uri.toFile(): File = File(this.path!!)

fun File.toMultipartBody(): MultipartBody.Part = MultipartBody.Part.createFormData(
    "images",
    this.name,
    this.asRequestBody("image/jpeg".toMediaTypeOrNull())
)

fun File.toAiMultipartBody(): MultipartBody.Part = MultipartBody.Part.createFormData(
    "image",
    this.name,
    this.asRequestBody("image/jpeg".toMediaTypeOrNull())
)


fun List<String>.toMajorItem(): List<MajorItem> {
    return this.map {
        MajorItem(it)
    }

}


fun ChatResponse.toChatItem(): List<ChatModel> {
    return this.map {

        ChatModel(it.user?.name.toString(), it.content.toString(), it.user?.profileImg.toString())
    }

}

fun List<String>.toInterestsItem(): List<InterestsItem> {
    return this.map {
        InterestsItem(it)
    }

}
