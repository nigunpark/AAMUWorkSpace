package com.aamu.aamuandroidapp.components.aamuplan.PlanDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanListWidthItem
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.components.Material3Card
import com.aamu.aamuandroidapp.ui.theme.typography
import com.aamu.aamuandroidapp.util.CustomChips

@Composable
fun SideContent(mapviewModel : AAMUPlanViewModel) {
    val planners by mapviewModel.planners.observeAsState(emptyList())
    val plannerSelectOne by mapviewModel.plannerSelectOne.observeAsState()
    Surface(elevation = 3.dp) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(5.dp),
        ) {
            item {
                Material3Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    backgroundColor = Color(0xffe9f6ff),
                    shape = RoundedCornerShape(4.dp),
                    elevation = 4.dp,
                ) {
                    Row {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(plannerSelectOne?.smallImage ?: R.drawable.no_image)
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Crop
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(60.dp)
                        )
                        Text(text = plannerSelectOne?.plannerdate ?: "날짜를 못받았어요",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp))
                    }
                }
                Spacer(modifier = Modifier.padding(bottom = 5.dp))

            }
            itemsIndexed(items = planners,
                itemContent = { index, planner ->
                    PlanDrawerList(mapviewModel,planner, index)
                    Spacer(modifier = Modifier.padding(bottom = 5.dp))
                    Divider(modifier = Modifier.alpha(0.1f))
                }
            )
        }
    }
}

@Composable
fun PlanDrawerList(mapviewModel : AAMUPlanViewModel, planner: Place, itemIndex: Int){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (planner.rbn==null){
            LazyRow(modifier = Modifier.fillMaxWidth(0.875f)){
                itemsIndexed(items = planner.dto?.addr?.split("&&")!!,
                    itemContent = { index, item ->
                        CustomChips(text = item)
                    }
                )
            }
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
        else {
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            ) {
                androidx.compose.material3.Text(
                    text = planner.dto?.title!!,
                    style = typography.h6.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                androidx.compose.material3.Text(
                    text = planner.dto?.addr ?: "",
                    style = typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(planner.dto?.smallimage?: R.drawable.no_image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
            )
        }
    }
}