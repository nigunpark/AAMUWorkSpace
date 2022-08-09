package com.aamu.aamuandroidapp.fragment.main.sub

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.routebbs.RouteBBSScreen
import com.aamu.aamuandroidapp.components.routebbs.RouteViewModel
import com.aamu.aamuandroidapp.components.routebbs.RouteViewModelFactory
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.fragment.main.MainFragmentDirections
import com.aamu.aamuandroidapp.ui.theme.ComposeCookBookTheme

sealed class RouteBBSHomeInteractionEvents {
    data class OpenBBSDetail(val bbs: AAMUBBSResponse, val imageId: Int = 0) : RouteBBSHomeInteractionEvents()
}

class RouteBBSFragment : Fragment() {

    private lateinit var navControllerHost : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            val viewModel: RouteViewModel = viewModel(
                factory = RouteViewModelFactory(LocalContext.current)
            )
            viewModel.getaamuBBSList()
            ComposeCookBookTheme {
                RouteBBSScreen(routeBBSHomeInteractionEvents = { event ->
                    handleInteractionEvents(event)
                })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControllerHost = findNavController()
    }

    private fun handleInteractionEvents(
        interactionEvents: RouteBBSHomeInteractionEvents
    ) {
        when (interactionEvents) {
            is RouteBBSHomeInteractionEvents.OpenBBSDetail -> {
                val action = MainFragmentDirections.actionMainFragmentToRouteBBSDetailFragment(interactionEvents.bbs.rbn!!)
                navControllerHost.navigate(action)
            }
        }
    }
}



