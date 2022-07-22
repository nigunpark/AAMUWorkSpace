package com.aamu.aamuandroidapp.components.aamuplan.PlanItems

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.data.model.Tweet
import com.aamu.aamuandroidapp.ui.theme.typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun PlanListWidthItem(planner: Place, itemIndex: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(planner.dto?.smallimage)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .padding(4.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f)
        ) {
            Text(
                text = planner.dto?.title!!,
                style = typography.h6.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = planner.dto?.addr!!,
                style = typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.padding(4.dp)
        )
    }
}


@Composable
fun PlanListVerticalItem(planner: Place, itemIndex: Int, selectedIndex: Int,lazyListState : LazyListState,lazyListState2 : LazyListState,coroutineScope: CoroutineScope) {
    Row(
        modifier = Modifier.selectable(
            selected = selectedIndex == itemIndex,
            onClick = {
                coroutineScope.launch {
                    lazyListState.scrollToItem(itemIndex+1)
                    lazyListState2.scrollToItem(itemIndex+1)
                }
            }
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(planner.dto?.smallimage)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop
            ),
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
                .padding(4.dp)
        )
    }
}