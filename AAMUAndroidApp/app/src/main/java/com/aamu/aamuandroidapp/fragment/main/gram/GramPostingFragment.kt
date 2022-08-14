package com.aamu.aamuandroidapp.fragment.main.gram

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.components.gram.posting.GramPosting
import com.aamu.aamuandroidapp.components.gram.posting.GramPostingViewModel
import com.aamu.aamuandroidapp.components.gram.posting.GramPostingViewModelFactory

class GramPostingFragment : Fragment() {

    private lateinit var navController : NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {

        navController = findNavController()
        val viewModel : GramPostingViewModel by viewModels<GramPostingViewModel>{
            GramPostingViewModelFactory(context)
        }
        setContent {
            GramPosting(viewModel = viewModel,goToAppSettings = { goToAppSettings() },navPopBackStack={navController.popBackStack()})
        }
    }

    private fun goToAppSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${this.requireActivity().packageName}")).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}