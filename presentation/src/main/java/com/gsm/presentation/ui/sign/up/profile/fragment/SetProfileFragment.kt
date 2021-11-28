package com.gsm.presentation.ui.sign.up.profile.fragment


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentSetProfileBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.sign.up.SignUpSignInMainActivity
import com.gsm.presentation.ui.test.activity.TestMainActivity
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.util.extension.toFile
import com.gsm.presentation.util.extension.toMultipartBody
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.*


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
            } catch (e: Exception) {
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
        with(binding) {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    fun getUserProfileImage() {
        var writePermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        var readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) { // 권한 없어서 요청 ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQ_STORAGE_PERMISSION) }

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1000
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE

            getResult.launch(intent)

        }

    }


    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }

    private fun postProfile(toMultipartBody: MultipartBody.Part?) {
        lifecycleScope.launch {
            viewModel.postProfile(token, toMultipartBody)
        }


    }

    @SuppressLint("Range")
    fun getPathFromUri(uri: Uri?): String? {
        val cursor: Cursor? = (activity as SignUpSignInMainActivity).contentResolver.query(
            uri!!,
            null,
            null,
            null,
            null
        )
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
                binding.nameEditTxt.text
            ))
        ) {
            viewModel.setProfileEmailNameProfile(
                binding.companyEmailEditText.text.toString(),
                binding.nameEditTxt.text.toString(),
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
            viewModel.isSuccess.observe(viewLifecycleOwner, EventObserver { success ->
                viewModel.pathProfileData.observe(viewLifecycleOwner, EventObserver { data ->
                    if (success) {
                        if (data.level == 0) {
                            startActivity(Intent(requireContext(), TestMainActivity::class.java))
                            (activity as SignUpSignInMainActivity).finish()
                        } else {
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            (activity as SignUpSignInMainActivity).finish()
                        }
                    } else {
                        Log.d("TAG", "getUserProfileAndSetting: 실패")
                        Toast.makeText(requireContext(), "실패", Toast.LENGTH_SHORT).show()
                    }
                })
            })


        }
    }
}


