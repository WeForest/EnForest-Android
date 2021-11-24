package com.gsm.presentation.ui.study.chat

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.navArgs
import com.gsm.presentation.R
import com.gsm.presentation.adapter.ChatAdapter
import com.gsm.presentation.base.BaseActivity
import com.gsm.presentation.databinding.FragmentGroupChatBinding
import com.gsm.presentation.util.App
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.*
import java.nio.channels.IllegalBlockingModeException
import java.util.*


@AndroidEntryPoint
class GroupChatFragment :
    BaseActivity<FragmentGroupChatBinding>(R.layout.fragment_group_chat) {
    private lateinit var chating_Text: EditText
    private lateinit var chat_Send_Button: Button

    private val viewModel: ProfileViewModel by viewModels()
    private var hasConnection: Boolean = false
    private val signViewModel: SignInViewModel by viewModels()
    private val args by navArgs<GroupChatFragmentArgs>()

    val TAG = "socket"
    lateinit var socket: Socket
    var token = ""

    //리사이클러뷰
    private var arrayList = arrayListOf<ChatModel>()
    private val mAdapter: ChatAdapter by lazy {
        ChatAdapter(this, arrayList, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chating_Text = binding.messageEdit
        chat_Send_Button = binding.sendBtn
        setAdapter()
        getToken()
        socket = App.get()

        try {
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            socket.on(Socket.EVENT_CONNECT, onConnectSuccess)
            socket.connect()

            Log.d(TAG, "onCreate: 소켓연결 성공 ${socket.connected()}")
            emitSetting()
            emitJoin()
            socket.on("sendMessage", sendMessage)

        } catch (e: URISyntaxException) {
        Log.d(TAG, "onCreate:  에러 ${e.printStackTrace()}")
        e.printStackTrace()
    } catch (ioe: IOException) {
        // 소켓 생성 과정에서 I/O 에러 발생.
        Log.e(TAG, "onCreate: ${ioe}", )
    } catch (uhe: UnknownHostException ) {
        // 소켓 생성 시 전달되는 호스트(www.unknown-host.com)의 IP를 식별할 수 없음.
        Log.e(TAG, "onCreate: ${uhe}", )
    } catch (se:SecurityException ) {
        Log.e(TAG, "onCreate: ${se}", )

        // security manager에서 허용되지 않은 기능 수행.
    } catch (se:IllegalArgumentException ) {
        Log.e(TAG, "onCreate: ${se}", )
        // 소켓 생성 시 전달되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
    }

    catch (se: SocketTimeoutException) {
        Log.e(TAG, "onCreate tiemout: ${se}", )
        // 소켓 생성 시 전달되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
    }
    catch (se: IllegalBlockingModeException) {
        Log.e(TAG, "onCreate : ${se}", )
        // 소켓 생성 시 전달되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
    }
    catch (se: SocketException) {
        Log.e(TAG, "onCreate : ${se}", )
        // 소켓 생성 시 전달되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
    }
    catch (se: NullPointerException) {
        Log.e(TAG, "onCreate : ${se}", )
        // 소켓 생성 시 전달되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
    }

        chat_Send_Button.setOnClickListener {
            //아이템 추가 부분
            sendMessage()

        }
    }

    private val onConnectError = Emitter.Listener {args->
        Log.d(TAG, "onConnectError: ${args[0]} ")
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                "Unable to connect to NodeJS server",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val onConnectSuccess = Emitter.Listener {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                "이걸 성공?",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun emitSetting() {
        //초기 설정
        val json = JSONObject()
        json.put("token", token)
        socket.emit("setting", json)

    }

    private fun emitJoin() {
        val userId = JSONObject()
        userId.put("token", token)
        userId.put("roomId", args.groupId)
        //socket.emit은 메세지 전송임
        socket.emit("join", userId)

    }


    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(this) {
            token = it.token
        }
    }

    private fun setAdapter() {
        binding.studyMeetingRecyclerView.apply {
            adapter = mAdapter
            showVertical(context)
        }
    }


    private var sendMessage: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread {
            Log.e("socket", "sendMessage return : $${args[0]}")
            val data = args[0] as JSONObject
            val name: String
            val message: String
            val profile_image: String
            try {
                Log.e("socket", "sendMessage return : $data")
                name = data.getString("name")
                message = data.getString("message")
                profile_image = data.getString("profileImg")

                val format = ChatModel(name, message, profile_image, "null")
                mAdapter.addItem(format)
                mAdapter.notifyDataSetChanged()
                Log.e("new me", name)
            } catch (e: Exception) {
                Log.d(TAG, "onNewMessage: 에러 ${e} ")
                return@runOnUiThread
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
            Log.d(
                TAG,
                "sendMessage: message  ${message} groupId : ${args.groupId} token : ${token}"
            )
            jsonObject.put("token", token)
            jsonObject.put("roomId", args.groupId)
            jsonObject.put("message", message)
            socket.emit("sendMessage", jsonObject)

        } catch (e: JSONException) {
            Log.d(TAG, "sendMessage: 에러 ${e}")
            e.printStackTrace()
        }
        Log.d(TAG, "sendMessage : object $jsonObject")


    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}