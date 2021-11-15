package com.gsm.presentation.ui.study.meeting

import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentStudyMeetingBinding
import com.gsm.presentation.ui.study.partner.PartnerRecyclerAdapter
import com.gsm.presentation.util.extension.showVertical
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket

class StudyMeetingFragment : BaseFragment<FragmentStudyMeetingBinding>(R.layout.fragment_study_meeting) {



    private fun initRecyclerView() {
        binding.studyMeetingRecyclerView.showVertical(requireContext())
        binding.studyMeetingRecyclerView.adapter = PartnerRecyclerAdapter()
    }
    fun main() {
        try {

            while (true) {

                val server = ServerSocket(7777)

                println("사용자 접속 대기중...")

                val socket = server.accept()

                val input = socket.getInputStream()
                val dataInputStream = DataInputStream(input)

                val output = socket.getOutputStream()
                val dataOutputStream = DataOutputStream(output)

                dataOutputStream.writeInt(7)
                dataOutputStream.writeUTF("서버 문자열")

                val intData = dataInputStream.readInt()
                val stringData = dataInputStream.readUTF()

                socket.close()
                server.close()

                println("Android 에서 받은 숫자 : $intData")
                println("Android 에서 받은 문자열 : $stringData")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}