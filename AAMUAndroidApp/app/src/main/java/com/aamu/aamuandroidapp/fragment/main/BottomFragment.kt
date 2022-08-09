package com.aamu.aamuandroidapp.fragment.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentMainBinding
import com.aamu.aamuandroidapp.fragment.main.sub.*
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import kotlin.reflect.KFunction1

class BottomFragment : Fragment(){

    private lateinit var binding: FragmentMainBinding

    private lateinit var viewModel : MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity(),MainViewModelFactory(context))[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            MeowBottom(viewModel, ::replace)
        }
    }

    fun replace(fragmet: Fragment) {
        val lottie = requireActivity().findViewById<LottieAnimationView>(R.id.waveanimat)

        if(fragmet is RouteBBSFragment) {
            lottie.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                LottieValueCallback(SimpleColorFilter(Color.parseColor("#4972AF")))
            )
        }
        else if(fragmet is HomeFragment){
            lottie.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                LottieValueCallback(SimpleColorFilter(Color.parseColor("#a5c3e7")))
            )
        }
        else {
            lottie.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                LottieValueCallback(SimpleColorFilter(Color.parseColor("#FAA307")))
            )
        }


        requireActivity().supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(binding.navMainFrame.id, fragmet).commit()
    }
}

@Composable
fun MeowBottom(
    viewModel: MainViewModel,
    replace: KFunction1<Fragment, Unit>
){
    val noticount by viewModel.noticount.observeAsState()
    AndroidView({viewModel.meowBottom}){
        it.setOnClickMenuListener {
            when (it.id) {
                1 -> { replace(HomeFragment()) }
                2 -> { replace(RouteBBSFragment()) }
                3 -> { replace(GramFragment()) }
                4 -> { replace(NotiFragment()) }
                5 -> { replace(InfoFragment()) }
            }
        }

        if(noticount!! > 0){
            it.setCount(4,noticount.toString())
        }
    }
}


