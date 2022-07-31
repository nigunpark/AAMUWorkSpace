package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.aamu.aamuandroidapp.components.routebbs.RouteBBSScreen
import com.aamu.aamuandroidapp.ui.theme.ComposeCookBookTheme

class RouteBBSFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {

        setContent {
            ComposeCookBookTheme {
                RouteBBSScreen()
            }
        }
    }
}