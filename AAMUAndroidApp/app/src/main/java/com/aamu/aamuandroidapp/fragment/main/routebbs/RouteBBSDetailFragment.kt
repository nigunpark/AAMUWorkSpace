package com.aamu.aamuandroidapp.fragment.main.routebbs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aamu.aamuandroidapp.components.routebbs.detail.RouteBBSDetail
import com.aamu.aamuandroidapp.util.setStatusBarOrigin
import com.aamu.aamuandroidapp.util.setStatusBarTransparent

class RouteBBSDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(inflater.context).apply {
        val args: RouteBBSDetailFragmentArgs by navArgs()
        setContent {
            RouteBBSDetail(args.rbn)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().setStatusBarTransparent()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().setStatusBarOrigin()
    }
}