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
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentMainBinding
import com.aamu.aamuandroidapp.fragment.main.sub.GramFragment
import com.aamu.aamuandroidapp.fragment.main.sub.HomeFragment
import com.aamu.aamuandroidapp.fragment.main.sub.InfoFragment
import com.aamu.aamuandroidapp.fragment.main.sub.RouteBBSFragment
import com.aamu.aamuandroidapp.util.PermissionUtils
import meow.bottomnavigation.MeowBottomNavigation

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var navControllerHost: NavController

    private lateinit var permissionUtils: PermissionUtils

    private lateinit var preferences: SharedPreferences

    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentMainBinding.inflate(layoutInflater)

        binding.bottomNav.apply {
            add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            add(MeowBottomNavigation.Model(2, R.drawable.ic_explore))
            add(MeowBottomNavigation.Model(3, R.drawable.ic_instagram))
            add(MeowBottomNavigation.Model(4, R.drawable.ic_account))
            show(1)
        }
        replace(HomeFragment())

        binding.bottomNav.setOnClickMenuListener {
            when (it.id) {
                1 -> replace(HomeFragment())
                2 -> replace(RouteBBSFragment())
                3 -> replace(GramFragment())
                4 -> replace(InfoFragment())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

    private fun replace(fragmet: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(binding.navMainFrame.id, fragmet).commit()
    }
}