package com.aamu.aamuandroidapp.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoreInfoSection(viewModel: ProfileScreenViewModel) {
    val context = LocalContext.current

    val plannerSelectList by viewModel.plannerSelectList.observeAsState(emptyList())
    val error by viewModel.errorLiveData.observeAsState()

    val bookMarkSelectList by viewModel.bookMarkSelectList.observeAsState(emptyList())
    val errorBook by viewModel.errorBookMarkLiveData.observeAsState()

    viewModel.getPlannerSelectList()
    viewModel.getPlannerBookMarkSelectList()

    Text(
        text = "내가 생성한 일정",
        style = typography.h6,
        color = aamuorange,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    if(plannerSelectList.isNotEmpty()) {
        LazyRow {
            items(plannerSelectList) { place ->
                Surface(elevation = 5.dp) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                    ) {
                        Column(
                            modifier = Modifier.background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(place.smallImage ?: R.drawable.no_image)
                                        .crossfade(true)
                                        .build(),
                                    contentScale = ContentScale.Crop
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(130.dp)
                                    .shadow(1.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = place.title ?: "",
                                    style = typography.h6.copy(fontSize = 12.sp),
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }

    }
    Text(
        text = "내가 북마크한 일정",
        style = typography.h6,
        color = aamuorange,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    if(bookMarkSelectList.isNotEmpty()) {
        LazyRow {
            items(bookMarkSelectList) { place ->
                Surface(elevation = 5.dp) {
                    Box(modifier = Modifier.clip(RoundedCornerShape(5.dp))) {
                        Column(
                            modifier = Modifier.background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(
                                            place.photo?.get(0)
                                                ?: R.drawable.no_image
                                        )
                                        .crossfade(true)
                                        .build(),
                                    contentScale = ContentScale.Crop
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(130.dp)
                                    .shadow(1.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = place.title ?: "",
                                    style = typography.h6.copy(fontSize = 12.sp),
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}
