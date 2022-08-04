package com.aamu.aamuandroidapp.fragment.init

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.login.LoginScreen
import com.aamu.aamuandroidapp.components.login.LoginVideo
import com.aamu.aamuandroidapp.components.login.LoginViewModel
import com.aamu.aamuandroidapp.components.login.LoginViewModelFactory
import com.aamu.aamuandroidapp.databinding.FragmentLoginBinding
import com.aamu.aamuandroidapp.util.setContextapp
import com.aamu.aamuandroidapp.util.setStatusBarOrigin
import com.aamu.aamuandroidapp.util.setStatusBarTransparent
import com.google.common.reflect.Reflection
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

        navController = findNavController()

//        binding.cirLoginButton.setOnClickListener{
//            binding.cirLoginButton.startAnimation()
//            Handler(Looper.getMainLooper()).postDelayed({
//                navController.navigate(R.id.action_loginFragment_to_mainFragment)
//            },100)
//
//        }
        binding.loginCompose.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.loginCompose.setContent {
            LoginOnboarding()
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

    private fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("travel", "raw", requireActivity().packageName)
        val videoUri = "android.resource://${requireActivity().packageName}/$rawId"
        return Uri.parse(videoUri)
    }

    @SuppressLint("UnusedCrossfadeTargetStateParameter")
    @Composable
    fun LoginOnboarding() {
        val coroutineScope = rememberCoroutineScope()
        val viewModel: LoginViewModel = viewModel(
            factory = LoginViewModelFactory()
        )
        val token by viewModel.token.observeAsState()
        val islogin by viewModel.islogin.observeAsState()
        var loginfail by remember { mutableStateOf(false) }
        if(token!=null){
            val preferences: SharedPreferences =
                LocalContext.current.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            preferences.edit().putString("token", token).commit()
            viewModel.isok()
            if(islogin == true) {
                navController.navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }
        else {
//            LoginScreen(loginfail = loginfail){
//                coroutineScope.launch {
//                    delay(2000)
//                    loginfail = true
//                }
//            }
            LoginVideo(videoUri = getVideoUri(), loginfail = loginfail) {
                coroutineScope.launch {
                    loginfail = false
                    delay(2000)
                    loginfail = true
                }
            }
        }
    }
}