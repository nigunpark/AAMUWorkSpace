package com.aamu.aamuandroidapp.fragment.main

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentMainBinding
import com.aamu.aamuandroidapp.fragment.main.sub.GramFragment
import com.aamu.aamuandroidapp.fragment.main.sub.HomeFragment
import com.aamu.aamuandroidapp.fragment.main.sub.InfoFragment
import com.aamu.aamuandroidapp.fragment.main.sub.RouteBBSFragment
import com.aamu.aamuandroidapp.util.PermissionUtils
import com.aamu.aamuandroidapp.util.stomp
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.reactivex.disposables.Disposable
import meow.bottomnavigation.MeowBottomNavigation

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var navControllerHost: NavController

    private lateinit var permissionUtils: PermissionUtils

    private lateinit var preferences: SharedPreferences

    private lateinit var topic: Disposable

    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var viewModel : MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        Log.i("com.aamu.aamu",viewModel.fromthan.value ?: "없음")
        Log.i("com.aamu.aamu",viewModel.no.value.toString())
        replace(HomeFragment())

        binding.bottomNav.setContent {
            MeowBottom(context)
        }

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

    @Composable
    fun MeowBottom(
        context: Context
    ){
        AndroidView({MeowBottomNavigation(context)}){
            it.apply {
                hasAnimation =true
                backgroundBottomColor = Color.parseColor("#ffffff")
                circleColor = Color.parseColor("#ffffff")
                countBackgroundColor = Color.parseColor("#ff6f00")
                countTextColor = Color.parseColor("#ffffff")
                defaultIconColor = Color.parseColor("#90a4ae")
//                rippleColor = Color.parseColor("#2f424242")
                selectedIconColor = Color.parseColor("#3c415e")
//                shadowColor = Color.parseColor("#1f212121")
            }

            it.apply {
                add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
                add(MeowBottomNavigation.Model(2, R.drawable.ic_explore))
                add(MeowBottomNavigation.Model(3, R.drawable.ic_instagram))
                add(MeowBottomNavigation.Model(4, R.drawable.ic_notification))
                add(MeowBottomNavigation.Model(5, R.drawable.ic_account))
                show(1)

                setCount(4, "3")
            }

            it.setOnClickMenuListener {
                when (it.id) {
                    1 -> replace(HomeFragment())
                    2 -> replace(RouteBBSFragment())
                    3 -> replace(GramFragment())
                    4 -> replace(InfoFragment())
                    5 -> replace(InfoFragment())
                }
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

        val preferences : SharedPreferences = context?.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)!!
        val userid : String? = preferences.getString("id",null)
        topic = stomp.join("/queue/notification/"+userid).subscribe{

            val mapper = jacksonObjectMapper()
            val message : Map<*,*> = mapper.readValue(it, Map::class.java)

            Log.i("alertfcm","메인에서옴 : " + message)
        }


    }

    private fun replace(fragmet: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(binding.navMainFrame.id, fragmet).commit()
    }
}