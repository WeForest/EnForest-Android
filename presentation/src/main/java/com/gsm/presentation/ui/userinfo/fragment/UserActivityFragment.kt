package com.gsm.presentation.ui.userinfo.fragment

import android.annotation.SuppressLint
import android.app.Activity
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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentUserActivityBinding
import com.gsm.presentation.ui.sign.up.SignUpSignInMainActivity
import com.gsm.presentation.util.extension.toMultipartBody
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UserActivityFragment : Fragment() {

    private lateinit var binding: FragmentUserActivityBinding
    private lateinit var getResult: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_activity, container, false
        )

        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            try {

                Log.d("postProfile", "onCreateView: ${result?.data?.data}")
                val file = File(getPathFromUri(result.data?.data))
//                    postProfile(file.toMultipartBody())

                result?.data?.data?.path

//                    binding.profileImageView.setImageURI(result.data?.data)


            } catch (e: Exception) {
                Log.d("TAG", "onCreateView: ${e}")
            }
        }
        binding.fragment = this
        onclickCamera()

        return binding.root
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