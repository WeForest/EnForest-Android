package com.gsm.presentation.ui.study.chat

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.gsm.presentation.R
import com.gsm.presentation.adapter.ChatAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentGroupChatBinding
import com.gsm.presentation.ui.chat.ChatModel
import com.gsm.presentation.util.App
import com.gsm.presentation.util.Constant
import com.gsm.presentation.util.DataState
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.ai.AiViewModel
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.EngineIOException
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.net.URISyntaxException
import java.util.*


@AndroidEntryPoint
class GroupChatFragment :
    BaseFragment<FragmentGroupChatBinding>(R.layout.fragment_group_chat) {
    private lateinit var chating_Text: EditText
    private lateinit var chat_Send_Button: Button
    private val signViewModel: SignInViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val aiViewModel : AiViewModel by viewModels()
    private val args by navArgs<GroupChatFragmentArgs>()
    val TAG = "ChatFragment"
    lateinit var socket: Socket
    var token = ""
    var nickName = ""
    var image = ""

    //리사이클러뷰
    private var arrayList = arrayListOf<ChatModel>()
    private val mAdapter: ChatAdapter by lazy {
        ChatAdapter(arrayList)
    }


    override fun FragmentGroupChatBinding.onCreateView() {
        getToken()
        observeNickName()
        observeImage()
        chating_Text = binding.messageEdit
        chat_Send_Button = binding.sendBtn
        binding.messageEdit.addTextChangedListener { text ->
            binding.sendBtn.isEnabled = text.toString() != ""
        }
        setAdapter()


    }

//    private fun recyclerViewSetting(){
//        binding.studyMeetingRecyclerView.addOnLayoutChangeListener(View.OnLayoutChangeListener(
//
//        ))
//    }

    private fun setData() {
        lifecycleScope.launch {
            args.chat.id?.let { profileViewModel.getChatLog(it) }
            profileViewModel.chatLog.observe(viewLifecycleOwner) {
                Log.d(TAG, "setData: ${it}")
                mAdapter.setData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun FragmentGroupChatBinding.onViewCreated() {

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        chat_Send_Button.setOnClickListener {
            //아이템 추가 부분
            sendMessage()

        }
        setData()
        observe()
    }

    private fun observeImage() {
        signViewModel.readImage.asLiveData().observe(viewLifecycleOwner) {
            Log.d(TAG, "observeImage: ${it.profileImage}")
            image = it.profileImage
        }
    }


    private fun settingSocket(token: String) {
        try {
            val opts: IO.Options = IO.Options()
            opts.transports = arrayOf(WebSocket.NAME)
            socket =
                IO.socket(
                    "http://ec2-15-165-35-92.ap-northeast-2.compute.amazonaws.com:81/chat",
                    opts
                )
            socket.connect()

            val dd = JSONObject()
            Log.d(TAG, "onViewCreated: ${token}")
            dd.put("token", token)
            socket.emit("setting", dd)
            emitJoin()

            socket.on(Socket.EVENT_CONNECT, onConnectSuccess)

            socket.on("sendMessage", sendMessage)



            socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
                Log.i(TAG, "onCreateView error: ${args[0]}")
            }
            socket.on(Socket.EVENT_DISCONNECT) { args ->
                // 소켓 서버 연결이 끊어질 경우에 호출됩니다.
                Log.i("Socket", "Disconnet: ${args[0]}")
            }
//
        } catch (e: URISyntaxException) {
            Log.d(TAG, "onCreate:  에러 ${e.printStackTrace()}")
            e.printStackTrace()
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "onCreate:  에러 ${e.printStackTrace()}")
            e.printStackTrace()
        } catch (e: EngineIOException) {
            Log.d(TAG, "onCreate:  에러 ${e.printStackTrace()}")
            e.printStackTrace()
        }
    }

    private val onConnectError = Emitter.Listener { args ->
        Log.d(TAG, "onConnectError: ${args[0]} ")
        lifecycleScope.launch {
            Toast.makeText(
                requireContext(),
                "Unable to connect to NodeJS server",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val onConnectSuccess = Emitter.Listener {
        lifecycleScope.launch {
            Log.d(TAG, "성공: ")
        }
    }


    private fun emitJoin() {
        val userId = JSONObject()
        userId.put("token", token)
        userId.put("roomId", args.chat?.id)
        //socket.emit은 메세지 전송임
        socket.emit("join", userId)

    }


    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            Log.d(TAG, "getToken: ${it.token}")
            token = it.token
            settingSocket(token)
        }
    }

    private fun observeNickName() {
        profileViewModel.readName.asLiveData().observe(viewLifecycleOwner) {
            nickName = it.name
        }
    }


    private fun setAdapter() {
        binding.studyMeetingRecyclerView.apply {
            adapter = mAdapter
            showVertical(context)
        }
    }


    private var sendMessage: Emitter.Listener = Emitter.Listener { args ->
        lifecycleScope.launch {
            Log.e("socket", "sendMessage return : $${args[0]}")
            val data = args[0] as JSONArray
            val name: String
            val message: String
            val profile_image: String
            try {
                Log.e("socket", "sendMessage return : $data")
                name = data.getString(0)
                message = data.getString(2)
                profile_image = data.getString(1)
                Log.d(TAG, "sendMessage: ${name} ${message} ${profile_image}")

                val format = ChatModel(name, message, profile_image)
                mAdapter.addItem(format)
                Log.e("new me", name)
            } catch (e: Exception) {
                Log.d(TAG, "onNewMessage: 에러 ${e} ")
                return@launch
            }
        }
    }


    // send button 을 누르면 server 에 있는 sendMessage 가 호출된다.
    private fun sendMessage() {


        val message = binding.messageEdit.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(message)) {
            return
        }
        binding.messageEdit.setText("")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("token", token)
            jsonObject.put("roomId", args.chat?.id)
            jsonObject.put("message", message)
            socket.emit("sendMessage", jsonObject)

            mAdapter.addItem(
                ChatModel(
                    nickname = nickName,
                    contents = message,
                    image
                )
            )
            Log.d(TAG, "sendMessage: ${message}")
            abuseText(message)

            binding.studyMeetingRecyclerView.smoothScrollToPosition(mAdapter.itemCount);
        } catch (e: JSONException) {
            Log.d(TAG, "sendMessage: 에러 ${e}")
            e.printStackTrace()
        }


    }

    private fun abuseText(message:String){
        lifecycleScope.launch {
            aiViewModel.abuseText(message)
        }

    }

    // text가 욕설이 포함되어있는지 검사
    private fun observe() = with(aiViewModel) {
        abuseData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                  
                    Log.d(TAG, "observe: 성공 ${it.data}")
                }
                is DataState.Failure -> {
                    Log.d(TAG, "observe: 실패 ${it.message}")
                }
                is DataState.Loading -> {
             


                    Log.d(TAG, "observe: 로딩중..")
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}

