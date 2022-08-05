package com.aamu.aamuandroidapp.fragment.main.gram

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aamu.aamuandroidapp.components.gram.detail.GramDetail
import com.aamu.aamuandroidapp.fragment.main.routebbs.RouteBBSDetailFragmentArgs

class GramDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
            this.rootView?.setOnApplyWindowInsetsListener { _, insets ->
                val imeHeight = insets.getInsets(android.view.WindowInsets.Type.ime()).bottom
                val navigationHeight = insets.getInsets(android.view.WindowInsets.Type.navigationBars()).bottom
                val bottomInset = if (imeHeight == 0) navigationHeight else imeHeight
                this.rootView?.setPadding(0, 0, 0, bottomInset)
                insets
            }
        } else {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        val args: GramDetailFragmentArgs by navArgs()

        setContent {
            GramDetail(args.lno)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        } else {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }
}