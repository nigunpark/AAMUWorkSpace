package com.aamu.aamuandroidapp.fragment.main

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.databinding.FragmentMainBinding
import com.aamu.aamuandroidapp.fragment.main.sub.GramFragment
import com.aamu.aamuandroidapp.fragment.main.sub.HomeFragment
import com.aamu.aamuandroidapp.fragment.main.sub.InfoFragment
import com.aamu.aamuandroidapp.fragment.main.sub.RouteBBSFragment
import com.aamu.aamuandroidapp.util.PermissionUtils
import com.aamu.aamuandroidapp.util.stomp
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.reactivex.disposables.Disposable
import kotlin.math.absoluteValue
import kotlin.properties.Delegates
import kotlin.reflect.KFunction1

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var navControllerHost: NavController

    private lateinit var permissionUtils: PermissionUtils

    private lateinit var preferences: SharedPreferences

    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var viewModel : MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity(),MainViewModelFactory(context))[MainViewModel::class.java]

        Log.i("com.aamu.aamu",viewModel.fromthan.value ?: "없음")
        Log.i("com.aamu.aamu",viewModel.no.value.toString())
        requireActivity().supportFragmentManager.beginTransaction().replace(binding.bottomfragment.id,BottomFragment()).commit()
        replace(HomeFragment())

//        binding.bottomNav.apply {
//            add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
//            add(MeowBottomNavigation.Model(2, R.drawable.ic_explore))
//            add(MeowBottomNavigation.Model(3, R.drawable.ic_instagram))
//            add(MeowBottomNavigation.Model(4, R.drawable.ic_account))
//            show(1)
//        }
//        replace(HomeFragment())
//
//        binding.bottomNav.setOnClickMenuListener {
//            when (it.id) {
//                1 -> replace(HomeFragment())
//                2 -> replace(RouteBBSFragment())
//                3 -> replace(GramFragment())
//                4 -> replace(InfoFragment())
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.subNotification()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControllerHost = findNavController()

        permissionUtils = PermissionUtils(requireContext(), requireActivity(), permissions)

        preferences = requireContext().getSharedPreferences("local", Context.MODE_PRIVATE)

        val requestMultiplePermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
            { permissions ->
                permissions.entries.forEach {
                    if (!it.value) {
                        preferences.edit().putString("local", "N").commit()
                        return@registerForActivityResult
                    }
                    preferences.edit().putString("local", "Y").commit()
                }
            }
        if (!permissionUtils.checkPermission()) {
            //권한 요청
            requestMultiplePermissions.launch(permissions)
        } else {
            preferences.edit().putString("local", "Y").commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.unSubNotification()
    }

    fun replace(fragmet: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(binding.navMainFrame.id, fragmet).commit()
    }
}