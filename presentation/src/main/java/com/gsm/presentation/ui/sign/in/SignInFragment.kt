package com.gsm.presentation.ui.sign.`in`

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentSignInBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.sign.up.activity.SignUpSignInMainActivity
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: 2021-11-15
// 사용자 구글 로그인 -> 개인정보 입력 -> main
// // 토큰이 재발급 될때 -> 구글 로그인 -> main
// 개인정보를 입력하면  true 를 dataStore로 저장 하면안되지.
// 서버에서 미이 로그인된 유저 정보면 true값을 내보내는게 타당한듯
@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val signInViewModel: SignInViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 123


    override fun FragmentSignInBinding.onViewCreated() {


        view?.let { clickUserGoogleLogin(it) }
        observeToken()

//        getToken()
    }

    override fun FragmentSignInBinding.onCreateView() {
        binding.fragment = this@SignInFragment
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail() // email addresses도 요청함
            .requestIdToken(getString(R.string.client_id))
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


    }


    fun clickUserGoogleLogin(view: View) {
        binding.loginBtn.setOnClickListener {
            Log.d("TAG", "clickUserGoogleLogin: click")
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // 개인정보를 추가 했다면
    private fun readName() {
        profileViewModel.readName.asLiveData().observe(viewLifecycleOwner) {
            if (it.name == profileViewModel.defaultName) {
                findNavController()
                    .navigate(R.id.action_signInFragment_to_setProfileEndFragment)

            } else {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                (activity as SignUpSignInMainActivity).finish()
            }
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult(ApiException::class.java)
            if (acct != null) {

                
                // 구글로그인 시 token 나오면 저장
                postLogin(acct.idToken)
                // 개인정보 입력되어있으면 main 아니면 입력창
                Log.d("TAG", "handleSignInResult: ${acct.idToken}")

            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.statusCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }


//    private fun getToken() {
//        signInViewModel.token.observe(this, {
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra(token, it)
//            startActivity(intent)
//            finish()
//
//
//        })
//
//    }


    // token 이 제대로 됐는지 test
    private fun postLogin(token: String) {

        lifecycleScope.launch {
            Log.d("TAG", "postLogin: $token")
            signInViewModel.postLogin(token)

        }
    }



    private fun observeToken() {
        signInViewModel.tokenValue.observe(requireActivity(), EventObserver {
            if (it.isNotEmpty()) {
                signInViewModel.saveToken(it)
                readName()
                Toast.makeText(requireContext(), "성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "실패", Toast.LENGTH_SHORT).show()
            }
        })
    }


}