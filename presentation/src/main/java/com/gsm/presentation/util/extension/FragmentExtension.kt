package com.gsm.presentation.util.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsm.presentation.R
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    "name",
    "fileName",
    RequestBody.create("image/*".toMediaTypeOrNull(), this)
)
