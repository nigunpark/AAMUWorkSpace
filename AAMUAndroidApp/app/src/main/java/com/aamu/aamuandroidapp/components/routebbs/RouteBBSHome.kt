package com.aamu.aamuandroidapp.components.routebbs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.fragment.main.sub.RouteBBSHomeInteractionEvents
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
    val dominantColors = listOf(Color.White, cyan200)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradientBackground(dominantColors),
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


