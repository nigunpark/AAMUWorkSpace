package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.databinding.FragmentHomeBinding
import com.aamu.aamuandroidapp.ui.theme.yellow


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navControllerHost : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeCompose.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.homeCompose.setContent {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.White, Color.Transparent),
                    startY = maxHeight.value / 3,  // 1/3
                    endY = maxHeight.value
                )
                val imgLoader = ImageLoader(LocalContext.current).newBuilder().components {
                    if (Build.VERSION.SDK_INT >= 28) add(ImageDecoderDecoder.Factory())
                    else add(GifDecoder.Factory())
                }.build()
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(R.drawable.ocean)
                                .crossfade(true)
                                .build(),
                            imageLoader = imgLoader,
                            placeholder = null,
                            contentScale = ContentScale.FillHeight
                        ),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillHeight
                    )
                    Box(modifier = Modifier.matchParentSize().background(gradient))
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    GoAAMUPlan()
                }
            }
        }
        navControllerHost = findNavController()
    }


    @Composable
    fun GoAAMUPlan(){
        Text(text = "무계획자를 위한 여행 지침서")
        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(contentColor = yellow),
            onClick = {
                navControllerHost.navigate(R.id.action_mainFragment_to_plannerFragment)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "아무여행 하기")
        }
    }
    @Preview
    @Composable
    fun PreviewButtons() {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            GoAAMUPlan()
        }
    }

}


