package com.aamu.aamuandroidapp.fragment.init

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.login.LoginScreen
import com.aamu.aamuandroidapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

//        binding.cirLoginButton.setOnClickListener{
//            binding.cirLoginButton.startAnimation()
//            Handler(Looper.getMainLooper()).postDelayed({
//                navController.navigate(R.id.action_loginFragment_to_mainFragment)
//            },100)
//
//        }

        binding.loginCompose.setContent {
            LoginOnboarding()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private var callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!navController.popBackStack(R.id.loadingFragment,true)) {
                activity?.finish()
            }
        }

    }
    @SuppressLint("UnusedCrossfadeTargetStateParameter")
    @Composable
    fun LoginOnboarding() {
        var loggedIn by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        Crossfade(targetState = loggedIn) {
            LoginScreen {
                coroutineScope.launch {
                    delay(2000)
                    loggedIn = true
                }
            }
        }
    }
}