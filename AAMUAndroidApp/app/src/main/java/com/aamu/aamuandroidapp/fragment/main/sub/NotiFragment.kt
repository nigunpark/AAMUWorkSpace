package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aamu.aamuandroidapp.components.notification.NotificationList
import com.aamu.aamuandroidapp.fragment.main.MainViewModel
import com.aamu.aamuandroidapp.fragment.main.MainViewModelFactory

class NotiFragment : Fragment() {

    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(context))[MainViewModel::class.java]
        viewModel.getNotiList()
        setContent {
            NotificationList(viewModel)
        }
    }
}