package com.aamu.aamuandroidapp.components.routebbs

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.ComponentRegistry
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.fragment.main.sub.RouteBBSHomeInteractionEvents
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.cyan700
import com.aamu.aamuandroidapp.ui.theme.extensions.generateDominantColorState
import com.aamu.aamuandroidapp.ui.theme.modifiers.verticalGradientBackground
import com.aamu.aamuandroidapp.ui.theme.typography



@Composable
fun RouteBBSScreen(routeBBSHomeInteractionEvents: (RouteBBSHomeInteractionEvents) -> Unit) {
    RouteBBSScreenContent(routeBBSHomeInteractionEvents)
}

@Composable
fun RouteBBSScreenContent(routeBBSHomeInteractionEvents: (RouteBBSHomeInteractionEvents) -> Unit) {
    //TODO dynamic gradient from poster via coil right now It's just getting from local images
    val imageId = remember { mutableStateOf(R.drawable.camelia) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()){
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.White, Color.Transparent),
            startY = maxHeight.value/3,  // 1/3
            endY = maxHeight.value
        )
        val imgLoader = ImageLoader(LocalContext.current).newBuilder().components {
            if (SDK_INT >= 28){
                add(ImageDecoderDecoder.Factory())
            }
            else{
                add(GifDecoder.Factory())
            }
        }.build()
        Box{
            Image(painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.deepdarkocean)
                    .crossfade(true)
                    .build(),
                imageLoader = imgLoader,
                placeholder = null,
                contentScale = ContentScale.FillHeight
            ),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            , contentScale = ContentScale.FillHeight)
            Box(modifier = Modifier.matchParentSize().background(gradient))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // use `item` for separate elements like headers
            // and `items` for lists of identical elements
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                Text(
                    text = "이런여행 어때",
                    style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(16.dp)
                )
            }
            item { RoutePager(imageId,routeBBSHomeInteractionEvents) }
        }
    }
}


