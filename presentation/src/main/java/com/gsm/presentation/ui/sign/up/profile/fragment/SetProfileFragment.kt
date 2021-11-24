package com.gsm.presentation.ui.sign.up.profile.fragment


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64.DEFAULT
import android.util.Base64.NO_WRAP
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.github.dhaval2404.imagepicker.ImagePicker
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentSetProfileBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.sign.up.SignUpSignInMainActivity
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.socket.engineio.parser.Base64
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream


//todo
//    val img: Bitmap = BitmapFactory.decodeStream(ins)
//                    ins?.close()
//                    val resized = Bitmap.createScaledBitmap(img, 256, 256, true)
//                    val byteArrayOutputStream = ByteArrayOutputStream()
//                    resized.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
//                    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
//                    Log.d("profile", "onActivityResult: ${(Base64.encodeToString(byteArray, NO_WRAP))}")
//                    postProfile(Base64.encodeToString(byteArray, NO_WRAP))
@AndroidEntryPoint
class SetProfileFragment : Fragment() {
    private lateinit var binding: FragmentSetProfileBinding
    private var proFileUri: Uri? = null
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val signViewModel by viewModels<SignInViewModel>()
    var token: String = ""
    var isJobSeeker = false
    var uploadFile: MultipartBody.Part? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_profile, container, false)

        binding.viewmodel = viewModel
        binding.fragment = this
        getToken()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserProfileAndSetting()
        isComponyOnclick()
        isJobSeekerOnclick()
        nextButton()
    }

    fun getUserProfileImage() {
        val intent = Intent()

        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        ImagePicker.with(this)
            .compress(1024)
            .start()
    }

    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }

    private fun postProfile(patch: MultipartBody.Part) {
        lifecycleScope.launch {
            viewModel.postProfile(token, patch)
        }


    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            2404 -> {
                proFileUri = data?.data
                var filepath = data?.data?.path
                Log.d("profile", "onActivityResult: $proFileUri")
                Log.d("profile", "onActivityResult: bitmap $filepath")

                Glide.with(this)
                    .load(proFileUri)
                    .into(binding.profileImageView)

                val ins: InputStream? = proFileUri?.let {
                    context?.contentResolver?.openInputStream(
                        it

                    )
                }
                val file: File? = File(filepath)

                var inputStream: InputStream? = null
                try {
                    inputStream = proFileUri?.let { context!!.contentResolver.openInputStream(it) }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

                val requestBody: RequestBody = RequestBody.create(
                    "image/jpg".toMediaTypeOrNull(),
                    (Base64.encodeToString(byteArray, DEFAULT))
                )
                postProfile(MultipartBody.Part.createFormData("postImg", file?.name, requestBody))
                ins?.close()
                Log.d(
                    "profile",
                    "onActivityResult: ${(Base64.encodeToString(byteArray, DEFAULT))}"
                )

                try {

                } catch (e: Exception) {

                }
            }
            ImagePicker.RESULT_ERROR -> {
                proFileUri = null
                Toast.makeText(
                    requireContext(),
                    "비정상적인 접근입니다. 다시한번 이미지를 선택해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else -> {
                Toast.makeText(requireContext(), "작업 취소됨. 다시한번 이미지를 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    fun getFullPath(ctx: Activity, uri: Uri): String {

        var result = ""
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        var cursor: Cursor? = ctx.contentResolver?.query(uri, filePathColumn, null, null, null)

        if (cursor == null) {
            result = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            var idx = cursor.getColumnIndex(filePathColumn[0])
            result = cursor.getString(idx)
            cursor.close()
        }
        Log.e("tag", "절대 $result")

        return result;
    }


    fun nextButton() {

        if (textNullTest()) {
            lifecycleScope.launch {
                viewModel.pathProfile(token, isJobSeeker)
                uploadFile?.let { postProfile(it) }

            }
        }

    }

    private fun textNullTest(): Boolean {
        return if (!(TextUtils.isEmpty(binding.companyEmailEditText.text)) && !(TextUtils.isEmpty(
                binding.companyEditText.text
            ))
        ) {
            viewModel.setProfileEmailNameProfile(
                binding.companyEmailEditText.text.toString(),
                binding.companyEditText.text.toString(),
                binding.profileImageView.toString()
            )
            true
        } else {
            false
        }
    }

    fun isComponyOnclick() {
        isJobSeeker = true
        binding.isCompanyImageView.setBackgroundResource(R.drawable.profile_click_background)
        binding.isJobSickerImageView.setBackgroundResource(R.drawable.profile_background)
    }

    fun isJobSeekerOnclick() {
        isJobSeeker = false
        binding.isCompanyImageView.setBackgroundResource(R.drawable.profile_background)
        binding.isJobSickerImageView.setBackgroundResource(R.drawable.profile_click_background)
    }


    private fun getUserProfileAndSetting() {

        lifecycleScope.launch {
            viewModel.isSuccess.observe(viewLifecycleOwner, EventObserver {


                if (it) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    (activity as SignUpSignInMainActivity).finish()
                } else {
                    Log.d("TAG", "getUserProfileAndSetting: 실패")
                    Toast.makeText(requireContext(), "실패", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}


