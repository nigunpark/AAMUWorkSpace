package com.aamu.aamuandroidapp.fragment.init

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentLoadingBinding
import com.aamu.aamuandroidapp.util.setStatusBarOrigin
import com.aamu.aamuandroidapp.util.setStatusBarTransparent

class LoadingFragment : Fragment() {

    private lateinit var binding: FragmentLoadingBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    override fun onResume() {
        super.onResume()
        requireActivity().setStatusBarTransparent()
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(R.id.action_loadingFragment_to_loginFragment)
        },100)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().setStatusBarOrigin()
    }
}