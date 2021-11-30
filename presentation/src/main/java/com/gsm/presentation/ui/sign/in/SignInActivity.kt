package com.gsm.presentation.ui.sign.`in`

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseActivity
import com.gsm.presentation.databinding.ActivitySignInBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.sign.up.SignUpSignInMainActivity
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val profileViewModel: ProfileViewModel by viewModels()

    private val signInViewModel: SignInViewModel by viewModels()

    var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fragment = this@SignInActivity
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail() // email addresses도 요청함
            .requestIdToken(getString(R.string.client_id))
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        observeToken()

        clickUserGoogleLogin()
        permissionFile()
    }





    private fun permissionFile() {
        var writePermission =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        var readPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) { // 권한 없어서 요청 ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQ_STORAGE_PERMISSION) }

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1000
            )
        }
    }
    fun clickUserGoogleLogin() {
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
        profileViewModel.readName.asLiveData().observe(this) {
            if (it.name == profileViewModel.defaultName) {

                startActivity(Intent(this, SignUpSignInMainActivity::class.java))
                finish()

            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
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
        signInViewModel.tokenValue.observe(this, EventObserver {
            Log.d("TAG", "observeToken: ${it}")
            if (it.isNotEmpty()) {
                signInViewModel.saveToken(it)
                readName()
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
            }
        })
    }


}