package com.aamu.aamuandroidapp.fragment.main.planner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail.PlaceDetail
import com.aamu.aamuandroidapp.fragment.main.routebbs.RouteBBSDetailFragmentArgs

class PlaceDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(inflater.context).apply {
        val args: PlaceDetailFragmentArgs by navArgs()
        setContent {
            PlaceDetail(args.place!!)
        }
    }
}