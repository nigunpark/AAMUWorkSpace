package com.aamu.aamuandroidapp.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentMainBinding
import com.aamu.aamuandroidapp.fragment.main.sub.GramFragment
import com.aamu.aamuandroidapp.fragment.main.sub.HomeFragment
import com.aamu.aamuandroidapp.fragment.main.sub.InfoFragment
import com.aamu.aamuandroidapp.fragment.main.sub.RouteBBSFragment
import meow.bottomnavigation.MeowBottomNavigation

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding

    private lateinit var navControllerHost : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControllerHost = Navigation.findNavController(view)

        binding.bottomNav.apply {
            add(MeowBottomNavigation.Model(1,R.drawable.ic_home))
            add(MeowBottomNavigation.Model(2,R.drawable.ic_explore))
            add(MeowBottomNavigation.Model(3,R.drawable.ic_message))
            add(MeowBottomNavigation.Model(4,R.drawable.ic_account))
            show(1)
        }
        replace(HomeFragment())

        binding.bottomNav.setOnClickMenuListener{
            when (it.id){
                1 -> replace(HomeFragment())
                2 -> replace(RouteBBSFragment())
                3 -> replace(GramFragment())
                4 -> replace(InfoFragment())
            }
        }

    }

    private fun replace(fragmet : Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.navMainFrame.id,fragmet).commit()
    }
}