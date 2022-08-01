package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aamu.aamuandroidapp.components.routebbs.RouteBBSScreen
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.fragment.main.MainFragment
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

        navControllerHost = Navigation.findNavController(rootView)

        setContent {
            ComposeCookBookTheme {
                RouteBBSScreen(routeBBSHomeInteractionEvents = { event ->
                    handleInteractionEvents(event)
                })
            }
        }
    }

    private fun handleInteractionEvents(
        interactionEvents: RouteBBSHomeInteractionEvents
    ) {
        when (interactionEvents) {
            is RouteBBSHomeInteractionEvents.OpenBBSDetail -> {
//                val action = MainFragmentDirections.actionMainFragmentToRouteBBSDetailFragment(interactionEvents.bbs.rbn!!)
//                navControllerHost.navigate(action)
            }
        }
    }
}



