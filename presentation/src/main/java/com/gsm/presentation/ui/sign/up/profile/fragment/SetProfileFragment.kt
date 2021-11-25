package com.gsm.presentation.ui.sign.up.profile.fragment


import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentSetProfileBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.sign.up.SignUpSignInMainActivity
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.util.extension.toFile
import com.gsm.presentation.util.extension.toMultipartBody
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.*


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
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val signViewModel by viewModels<SignInViewModel>()
    var token: String = ""
    var isJobSeeker = false

    private lateinit var getResult: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_profile, container, false)

        binding.viewmodel = viewModel
        binding.fragment = this
        getToken()

        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            try {
                if (result.resultCode == RESULT_OK) {
                    Log.d("postProfile", "onCreateView: ${result?.data?.data}")
                   val file = File(getPathFromUri(result.data?.data))
                    postProfile(file.toMultipartBody())

                    result?.data?.data?.path

                    binding.profileImageView.setImageURI(result.data?.data)
                }
            }catch (e:Exception){
                Log.d("TAG", "onCreateView: ${e}")
            }
        }


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

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE

        getResult.launch(intent)


        ImagePicker.with(this)
            .compress(1024)
            .start()
    }


    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }

    private fun postProfile(toMultipartBody: MultipartBody.Part?) {
        lifecycleScope.launch {
            viewModel.postProfile(token,toMultipartBody)
        }


    }
    @SuppressLint("Range")
    fun getPathFromUri(uri: Uri?): String? {
        val cursor: Cursor? = (activity as SignUpSignInMainActivity).contentResolver.query(uri!!, null, null, null, null)
        cursor?.moveToNext()
        val path: String? = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        Log.d("postProfile", "getPathFromUri: ${path} ")
        return path
    }

    fun nextButton() {

        if (textNullTest()) {
            lifecycleScope.launch {
                viewModel.pathProfile(token, isJobSeeker)

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


