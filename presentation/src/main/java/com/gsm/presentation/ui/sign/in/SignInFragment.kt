package com.gsm.presentation.ui.sign.`in`

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentSignInBinding
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val signInViewModel: SignInViewModel by viewModels()

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


    override fun onStart() {
        super.onStart()
        val gsa = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (gsa != null && gsa.id != null) {
            postLogin(gsa.idToken)

        }
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

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult(ApiException::class.java)
            if (acct != null) {

                postLogin(acct.idToken)
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
                // token이 제대로 나오면 저장하고
                signInViewModel.saveToken(it)
                //화면이동하고
                view?.findNavController()
                    ?.navigate(R.id.action_signInFragment_to_signUpNameFragment)
                // Toast를 띄운다.
                Toast.makeText(requireContext(), "성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "실패", Toast.LENGTH_SHORT).show()
            }
        })
    }


}