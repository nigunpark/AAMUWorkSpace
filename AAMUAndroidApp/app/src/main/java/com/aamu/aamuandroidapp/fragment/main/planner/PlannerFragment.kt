package com.aamu.aamuandroidapp.fragment.main.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanHome
import com.aamu.aamuandroidapp.components.aamuplan.mapView
import com.aamu.aamuandroidapp.components.aamuplan.KakaoMap
import com.aamu.aamuandroidapp.databinding.FragmentPlannerBinding
import com.aamu.aamuandroidapp.util.setStatusBarOrigin
import com.aamu.aamuandroidapp.util.setStatusBarTransparent


class PlannerFragment : Fragment() {

    private lateinit var binding: FragmentPlannerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlannerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.plannerCompose.setContent {
            AAMUPlanHome()
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
}
