package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.components.profile.ProfileScreen


class InfoFragment : Fragment() {

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View=ComposeView(inflater.context).apply {
        navController = findNavController()

        setContent {
            ProfileScreen(navController)
        }
    }
}