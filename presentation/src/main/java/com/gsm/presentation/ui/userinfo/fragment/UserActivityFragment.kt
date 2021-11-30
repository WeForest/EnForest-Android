package com.gsm.presentation.ui.userinfo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.adapter.ConferenceAdapter
import com.gsm.presentation.databinding.FragmentUserActivityBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.util.DataState
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.util.extension.toAiMultipartBody
import com.gsm.presentation.util.extension.toMultipartBody
import com.gsm.presentation.viewmodel.ai.AiViewModel
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

@AndroidEntryPoint
class UserActivityFragment : Fragment() {

    private lateinit var binding: FragmentUserActivityBinding
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private val aiViewModel: AiViewModel by viewModels()
    private val signViewModel: SignInViewModel by viewModels()
    private val viewModel: ProfileViewModel by viewModels()
    var token = ""

    private lateinit var file: File
    private val conferenceAdapter: ConferenceAdapter by lazy {
        ConferenceAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_activity, container, false
        )
        binding.fragment = this
        getToken()
        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            try {

                Log.d("postProfile", "onCreateView: ${result?.data?.data}")
                file= File(getPathFromUri(result.data?.data))
                postImage(file.toAiMultipartBody())
                observe(file.toMultipartBody())


            } catch (e: Exception) {
                Log.d("TAG", "onCreateView: ${e}")
            }
        }
        setAdapter()
        onclickCamera()

        return binding.root
    }

    private fun postImage(toMultipartBody: MultipartBody.Part?) {
        Log.d("ai", "postImage: ${toMultipartBody}")
        lifecycleScope.launch {
            if (toMultipartBody != null) {
                aiViewModel.postConferenceImage(toMultipartBody)
            }
        }


    }


    private fun observe(file:MultipartBody.Part) = with(aiViewModel) {
        conferenceData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    binding.imageView2.visibility = View.GONE
                    binding.conferenceLayout.visibility = View.VISIBLE
                    lifecycleScope.launch {
                        viewModel.pathConference(
                            token, file, it.data.conference.toString(),
                            it.data.name.toString()
                        )
                    }
                    Log.d(TAG, "observe: 성공 ${it.data}")
                }
                is DataState.Failure -> {
                    binding.imageView2.visibility = View.GONE
                    binding.conferenceLayout.visibility = View.VISIBLE
                    Log.d(TAG, "observe: 실패 ${it.message}")
                }
                is DataState.Loading -> {
                    binding.conferenceLayout.visibility = View.GONE
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView2.playAnimation();
                    binding.imageView2.loop(true)


                    Log.d(TAG, "observe: 로딩중..")
                }
            }
        }

    }

    private fun getConference(nickName: String) {
        lifecycleScope.launch {
            viewModel.getConference(nickName)
        }
    }

    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }

    private fun getNickName() {
        viewModel.readName.asLiveData().observe(viewLifecycleOwner) {
            getConference(it.name)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNickName()
        getConferenceData()
    }

    fun getConferenceData() {
        viewModel.conferenceData.observe(viewLifecycleOwner) {
            Log.d("TAG", "getConferenceData: ${it}")
            conferenceAdapter.setData(listOf(it))
        }
    }


    fun setAdapter() {
        binding.conferenceRecycler.apply {
            adapter = conferenceAdapter
            showVertical(requireContext())
        }
    }

    @SuppressLint("Range")
    fun getPathFromUri(uri: Uri?): String? {
        val cursor: Cursor? = (activity as MainActivity).contentResolver.query(
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

    private fun onclickCamera() {
        binding.conferenceLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE

            getResult.launch(intent)
        }
    }

    fun back() {

        if (findNavController().currentDestination?.id == R.id.userActivityFragment2) {
            findNavController().navigateUp()
        }
    }

}