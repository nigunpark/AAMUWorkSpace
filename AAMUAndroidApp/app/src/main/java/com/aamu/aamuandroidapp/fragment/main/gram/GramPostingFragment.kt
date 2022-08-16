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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModel
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModelFactory
import com.aamu.aamuandroidapp.components.gram.posting.GramPosting

class GramPostingFragment : Fragment() {

    private lateinit var navController : NavController

    private lateinit var viewModel : AAMUgramViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {

        navController = findNavController()

        viewModel = ViewModelProvider(requireActivity(),
            AAMUgramViewModelFactory(requireContext())
        )[AAMUgramViewModel::class.java]

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