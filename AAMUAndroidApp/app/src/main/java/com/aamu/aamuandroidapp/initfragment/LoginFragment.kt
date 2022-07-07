package com.aamu.aamuandroidapp.initfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentLoginBinding
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

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

        binding.bottmNav.apply {
            add(
                MeowBottomNavigation.Model(
                    1,
                    R.drawable.ic_home
                )
            )
            add(
                MeowBottomNavigation.Model(
                    2,
                    R.drawable.ic_explore
                )
            )
            add(
                MeowBottomNavigation.Model(
                    3,
                    R.drawable.ic_message
                )
            )
            add(
                MeowBottomNavigation.Model(
                    4,
                    R.drawable.ic_notification
                )
            )
            add(
                MeowBottomNavigation.Model(
                    5,
                    R.drawable.ic_account
                )
            )
        }

    }

}