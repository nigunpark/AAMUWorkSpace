package com.aamu.aamuandroidapp.fragment.main.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.components.chatlist.AAMUChatScreen
import com.aamu.aamuandroidapp.fragment.main.MainFragmentDirections

class ChatlistFragment : Fragment() {

    private lateinit var navController : NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(inflater.context).apply {

        navController = findNavController()

        setContent {
            AAMUChatScreen( onClick = { roomno,otherid ->
                val action =
                    ChatlistFragmentDirections.actionChatlistFragmentToConversationFragment(roomno,otherid)
                navController.navigate(action)
            }, backClick = {
                navController.popBackStack()
            })
        }
    }
}