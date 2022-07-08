package com.aamu.aamuandroidapp.initfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentLoadingBinding
import com.aamu.aamuandroidapp.util.setStatusBarOrigin
import com.aamu.aamuandroidapp.util.setStatusBarTransparent
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

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
        val worker : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        val runnable = Runnable {

            navController.navigate(R.id.action_loadingFragment_to_loginFragment)
        }
        //2초후에 스레드 실행하기
        worker.schedule(runnable,5, TimeUnit.SECONDS);
    }

    override fun onStop() {
        super.onStop()
        requireActivity().setStatusBarOrigin()
    }
}