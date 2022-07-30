package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.gram.AAMUgramHome
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.databinding.FragmentGramBinding
import com.aamu.aamuandroidapp.ui.theme.ComposeCookBookTheme

class GramFragment : Fragment() {

    private lateinit var binding: FragmentGramBinding
    private lateinit var navControllerHost : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGramBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControllerHost = Navigation.findNavController(view)

        binding.gramCompose.setContent {
            val posts = remember { DemoDataProvider.tweetList.filter { it.tweetImageId != 0 } }
            val profiles = remember { DemoDataProvider.tweetList }
            ComposeCookBookTheme {
                AAMUgramHome(
                    posts = posts,
                    profiles = profiles,
                    onLikeClicked = {},
                    onCommentsClicked = {},
                    onSendClicked = {},
                    onProfileClicked = {},
                    onMessagingClicked = {navControllerHost.navigate(R.id.action_mainFragment_to_conversationFragment)}
                )
            }
        }
    }
}