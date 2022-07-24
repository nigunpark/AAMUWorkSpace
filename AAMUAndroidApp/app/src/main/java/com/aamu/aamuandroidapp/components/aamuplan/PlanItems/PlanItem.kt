package com.aamu.aamuandroidapp.components.aamuplan.PlanItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun PlanListWidthItem(mapviewModel : AAMUPlanViewModel, planner: Place, itemIndex: Int,startMove : MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (planner.rbn==null){
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(55.dp)
                    .clip(shape = CircleShape)
                    .background(amber200),
                Alignment.Center
            ) {
                Text(
                    text = planner.dto?.title!!,
                    style = typography.h6.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        else {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(planner.dto?.smallimage?:R.drawable.no_image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .padding(4.dp)
                    .shadow(1.dp)
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
                    text = planner.dto?.addr ?: "",
                    style = typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = {
                mapviewModel.getPlanMove(planner)
                startMove.value = true
            }) {
                Icon(
                    painter = painterResource(R.drawable.blue_pin_icon),
                    contentDescription = "Localized description",
                    tint = Color.Unspecified
                )
            }
        }
    }
}


@Composable
fun PlanListVerticalItem(planner: Place, itemIndex: Int, selectedIndex: Int,lazyListState : LazyListState,lazyListStateVertical : LazyListState,coroutineScope: CoroutineScope) {
    Row(
        modifier = Modifier.selectable(
            selected = selectedIndex == itemIndex,
            onClick = {
                coroutineScope.launch {
                    lazyListState.scrollToItem(itemIndex+1)
                    lazyListStateVertical.scrollToItem(itemIndex+1)
                }
            }
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (planner.rbn==null){
            Surface(elevation = 5.dp, shape = CircleShape) {
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(shape = CircleShape)
                        .background(amber200),
                    Alignment.Center
                ) {
                    Text(
                        text = planner.dto?.title!!,
                        style = typography.h6.copy(fontSize = 16.sp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        else {
            Surface(elevation = 5.dp, shape = CircleShape) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(planner.dto?.smallimage?:R.drawable.no_image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape),
                )
            }
        }
    }
}