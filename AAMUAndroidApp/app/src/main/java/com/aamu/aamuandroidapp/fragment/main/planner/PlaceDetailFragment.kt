package com.aamu.aamuandroidapp.fragment.main.planner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail.PlaceDetail
import com.aamu.aamuandroidapp.fragment.main.routebbs.RouteBBSDetailFragmentArgs

class PlaceDetailFragment : Fragment() {

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(inflater.context).apply {
        val args: PlaceDetailFragmentArgs by navArgs()
        navController = findNavController()
        setContent {
//            PlaceDetail(args.place!!)
        }
    }
}