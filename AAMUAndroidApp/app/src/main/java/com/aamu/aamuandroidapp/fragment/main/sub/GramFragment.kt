package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.remember
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.databinding.FragmentGramBinding
import com.aamu.aamuandroidapp.mainfragment.sub.gramcomponents.InstagramHome
import com.aamu.aamuandroidapp.ui.theme.ComposeCookBookTheme

class GramFragment : Fragment() {

    private lateinit var binding: FragmentGramBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGramBinding.inflate(layoutInflater)

        binding.gramCompose.setContent {
            val posts = remember { DemoDataProvider.tweetList.filter { it.tweetImageId != 0 } }
            val profiles = remember { DemoDataProvider.tweetList }
            ComposeCookBookTheme {
                InstagramHome(
                    posts = posts,
                    profiles = profiles,
                    onLikeClicked = {},
                    onCommentsClicked = {},
                    onSendClicked = {},
                    onProfileClicked = {},
                    onMessagingClicked = {}
                )
            }
        }

        return binding.root
    }
}