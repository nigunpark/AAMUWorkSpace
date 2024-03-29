package com.aamu.aamuandroidapp.fragment.main.planner

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanHome
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModelFactory
import com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail.PlaceDetailViewModelFactory
import com.aamu.aamuandroidapp.databinding.FragmentPlannerBinding
import com.aamu.aamuandroidapp.util.setStatusBarOrigin
import com.aamu.aamuandroidapp.util.setStatusBarTransparent
import net.daum.mf.map.api.MapView


class PlannerFragment : Fragment() {

    private lateinit var binding: FragmentPlannerBinding

    private lateinit var navController : NavController

    private val mapViewModel: AAMUPlanViewModel by activityViewModels<AAMUPlanViewModel> {

        AAMUPlanViewModelFactory(requireContext(),navController)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlannerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        mapViewModel.mapView = MapView(requireContext())
        mapViewModel.setInitMap()
        val permission = requireContext().getSharedPreferences("local",Context.MODE_PRIVATE)
        val checkPermission = permission.getString("local","")


        binding.plannerCompose.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.plannerCompose.setContent {
            if(checkPermission != "Y"){
                navController.popBackStack()
            }
            AAMUPlanHome(navController,mapViewModel)
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
