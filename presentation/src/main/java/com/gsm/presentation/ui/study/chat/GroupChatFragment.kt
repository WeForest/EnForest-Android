package com.gsm.presentation.ui.study.chat

import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.gsm.presentation.R
import com.gsm.presentation.adapter.ChatAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentGroupChatBinding
import com.gsm.presentation.ui.chat.ChatModel
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class GroupChatFragment :
    BaseFragment<FragmentGroupChatBinding>(R.layout.fragment_group_chat) {
    private lateinit var chating_Text: EditText
    private lateinit var chat_Send_Button: Button
    private val db = FirebaseFirestore.getInstance()
    private val viewModel: ProfileViewModel by viewModels()
    private var hasConnection: Boolean = false
    private val signViewModel: SignInViewModel by viewModels()
    private val args by navArgs<GroupChatFragmentArgs>()
    private val chatList = arrayListOf<ChatModel>()
    val TAG = "chat"
    lateinit var socket: Socket
    var token = ""
    private lateinit var registration: ListenerRegistration
    //리사이클러뷰
    private var arrayList = arrayListOf<ChatModel>()
    private lateinit var mAdapter: ChatAdapter


    override fun FragmentGroupChatBinding.onCreateView() {
        chating_Text = binding.messageEdit
        chat_Send_Button = binding.sendBtn
        getToken()
        binding.chatTitleTxt.setText(args.chat.name)
        binding.messageEdit.addTextChangedListener { text ->
            binding.sendBtn.isEnabled = text.toString() != ""
        }
        mAdapter = ChatAdapter(args.chat.name.toString(), chatList)
        setAdapter()

    }

    override fun FragmentGroupChatBinding.onViewCreated() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        chatList.add(ChatModel("알림", "${args.chat.name} 닉네임으로 입장했습니다.", ""))
        val enterTime = Date(System.currentTimeMillis())

        registration = db.collection("Chat")
            .orderBy("time", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { snapshots, e ->
                // 오류 발생 시
                if (e != null) {
                    Log.w("ChatFragment", "Listen failed: $e")
                    return@addSnapshotListener
                }

                // 원하지 않는 문서 무시
                if (snapshots!!.metadata.isFromCache) return@addSnapshotListener

                // 문서 수신
                for (doc in snapshots.documentChanges) {
                    Log.d("chat", "onViewCreated: 문서 수신")
                    val timestamp = doc.document["time"] as Timestamp

                    // 문서가 추가될 경우 리사이클러 뷰에 추가
                    if (doc.type == DocumentChange.Type.ADDED && timestamp.toDate() > enterTime) {
                        val nickname = doc.document["nickname"].toString()
                        val contents = doc.document["contents"].toString()

                        // 타임스탬프를 한국 시간, 문자열로 바꿈
                        val sf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA)
                        sf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
                        val time = sf.format(timestamp.toDate())

                        val item = ChatModel(nickname, contents, time)
                        chatList.add(item)
                    }
                    mAdapter.notifyDataSetChanged()
                }
            }
        sendOnClick()


//        try {
//            val opts: IO.Options = io.socket.client.IO.Options()
//            opts.transports = arrayOf<String>(WebSocket.NAME)
//            opts.query = token
//            socket = io.socket.client.IO.socket("ws://192.168.219.103:81")
//            socket.send()
//            socket.connect()
//            val dd = JSONObject()
//            dd.put("token", token)
//            socket.emit("connects", dd)
//            emitJoin()
//
//            socket.on("sendMessage", sendMessage)
//
//
//
//            socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
//                Log.i(TAG, "onCreateView error: ${args[0]}")
//            }
//            socket.on(Socket.EVENT_DISCONNECT) { args ->
//                // 소켓 서버 연결이 끊어질 경우에 호출됩니다.
//                Log.i("Socket", "Disconnet: ${args[0]}")
//            }
////
//        } catch (e: URISyntaxException) {
//            Log.d(TAG, "onCreate:  에러 ${e.printStackTrace()}")
//            e.printStackTrace()
//        } catch (e: SocketTimeoutException) {
//            Log.d(TAG, "onCreate:  에러 ${e.printStackTrace()}")
//            e.printStackTrace()
//        } catch (e: EngineIOException) {
//            Log.d(TAG, "onCreate:  에러 ${e.printStackTrace()}")
//            e.printStackTrace()
//        }
//        chat_Send_Button.setOnClickListener {
//            //아이템 추가 부분
//            sendMessage()
//
//        }
    }

//    private val onConnectError = Emitter.Listener { args ->
//        Log.d(TAG, "onConnectError: ${args[0]} ")
//        lifecycleScope.launch {
//            Toast.makeText(
//                requireContext(),
//                "Unable to connect to NodeJS server",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//    private val onConnectSuccess = Emitter.Listener {
//        lifecycleScope.launch {
//            Toast.makeText(
//                requireContext(),
//                "성공",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//
//    private fun emitJoin() {
//        val userId = JSONObject()
//        userId.put("token", token)
//        userId.put("roomId", args.chat.id)
//        //socket.emit은 메세지 전송임
//        socket.emit("join", userId)
//
//    }


    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(this) {
            token = it.token
        }
    }

    private fun sendOnClick() {

        binding.sendBtn.setOnClickListener {
            // 입력 데이터
            val data = hashMapOf(
                "nickname" to args.chat.name,
                "contents" to binding.messageEdit.text.toString(),
                "time" to Timestamp.now(),
                )


            db.collection("Chat").add(data)
                .addOnSuccessListener {
                    binding.messageEdit.text.clear()
                    Log.w("ChatFragment", "Document added: $it")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "전송하는데 실패했습니다", Toast.LENGTH_SHORT).show()
                    Log.w("ChatFragment", "Error occurs: $e")
                }
        }
    }



    private fun setAdapter() {
        binding.studyMeetingRecyclerView.apply {
            adapter = mAdapter
            showVertical(context)
        }
    }


//    private var sendMessage: Emitter.Listener = Emitter.Listener { args ->
//        lifecycleScope.launch {
//            Log.e("socket", "sendMessage return : $${args[0]}")
//            val data = args[0] as JSONObject
//            val name: String
//            val message: String
//            val profile_image: String
//            try {
//                Log.e("socket", "sendMessage return : $data")
//                name = data.getString("name")
//                message = data.getString("message")
//                profile_image = data.getString("profileImg")
//                Log.d(TAG, "sendMessage: ${name} ${message} ${profile_image}")
//
//                val format = ChatModel(name, message, profile_image, "null")
//                mAdapter.addItem(format)
//                mAdapter.notifyDataSetChanged()
//                Log.e("new me", name)
//            } catch (e: Exception) {
//                Log.d(TAG, "onNewMessage: 에러 ${e} ")
//                return@launch
//            }
//        }
//    }
//
//
//    // send button 을 누르면 server 에 있는 sendMessage 가 호출된다.
//    private fun sendMessage() {
//
//
//        val message = binding.messageEdit.text.toString().trim { it <= ' ' }
//        if (TextUtils.isEmpty(message)) {
//            return
//        }
//        binding.messageEdit.setText("")
//        val jsonObject = JSONObject()
//        try {
//            jsonObject.put("token", token)
//            jsonObject.put("roomId", args.chat.id)
//            jsonObject.put("message", message)
//            socket.emit("sendMessage", jsonObject)
//
//        } catch (e: JSONException) {
//            Log.d(TAG, "sendMessage: 에러 ${e}")
//            e.printStackTrace()
//        }
//
//
//    }

    override fun onDestroy() {
        super.onDestroy()
//        socket.disconnect()
    }
}

