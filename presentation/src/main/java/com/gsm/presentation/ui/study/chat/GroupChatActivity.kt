package com.gsm.presentation.ui.study.chat

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseActivity
import com.gsm.presentation.databinding.ActivityGroupChatBinding
import com.gsm.presentation.viewmodel.group.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GroupChatActivity : BaseActivity<ActivityGroupChatBinding>(R.layout.activity_group_chat) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        with(binding) {
            chatBackBtn.setOnClickListener {
               findNavController(R.id.groupChatFragment).navigateUp()
            }
        }

    }
}