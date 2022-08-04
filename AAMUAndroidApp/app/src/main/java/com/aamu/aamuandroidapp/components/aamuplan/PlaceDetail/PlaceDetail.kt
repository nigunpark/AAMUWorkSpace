package com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.gram.image.PlanGramImageGrid
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.ui.theme.cyan500
import com.aamu.aamuandroidapp.ui.theme.typography
import com.aamu.aamuandroidapp.ui.theme.yellow
import com.aamu.aamuandroidapp.util.getContentTypeId
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaceDetail(mapviewModel: AAMUPlanViewModel,
                coroutineScope: CoroutineScope,
                isPlaceDetail: Boolean?,
                topbarhide : MutableState<Boolean>,
                startMove : MutableState<Boolean>,
                bottomSheetScaffoldState: BottomSheetScaffoldState,){

    val viewModel : PlaceDetailViewModel = viewModel(
        factory = PlaceDetailViewModelFactory(LocalContext.current)
    )

    mapviewModel.setPlanDetailCenter(mapviewModel.detailPlace.value!!)
    viewModel.getPlaceOne(mapviewModel.detailPlace.value!!)

    coroutineScope.launch {
        bottomSheetScaffoldState.bottomSheetState.expand()
    }

    val remembertopbar = remember { mutableStateOf(topbarhide.value) }
    val rememberstartmove = remember { mutableStateOf(startMove.value) }
    topbarhide.value = false
    startMove.value = false

    BackHandler(isPlaceDetail == true){
        topbarhide.value = remembertopbar.value
        startMove.value = rememberstartmove.value
        mapviewModel.isPlaceDetail.value = false
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    val placeLiveData by viewModel.placeLiveData.observeAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.TopStart)
            ) {
                FloatingActionButton(
                    onClick = {
                        topbarhide.value = remembertopbar.value
                        startMove.value = rememberstartmove.value
                        mapviewModel.isPlaceDetail.value = false
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    },
                    modifier = Modifier
                        .size(width = 50.dp, height = 50.dp)
                        .offset(x = 20.dp, y = 5.dp),
                    backgroundColor = Color.White,
                    shape = CircleShape
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            LazyColumn(modifier = Modifier.padding(20.dp)) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Column() {
                            Text(
                                text = placeLiveData?.title ?: "",
                                style = typography.h6.copy(fontSize = 25.sp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = getContentTypeId(placeLiveData?.contenttypeid ?: 0),
                                style = typography.subtitle2,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FaIcon(
                                faIcon = FaIcons.Star,
                                size = 25.dp,
                                tint = yellow
                            )
                            Text(text = "별점", fontSize = 20.sp)
                        }
                    }
                }
                item { Divider(modifier = Modifier.alpha(0.1f)) }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        FaIcon(
                            faIcon = FaIcons.MapMarker,
                            tint = cyan500.copy(alpha = 0.3f),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Text(
                            text = placeLiveData?.addr ?: "",
                            style = typography.h6.copy(fontSize = 12.sp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        FaIcon(
                            faIcon = FaIcons.Clock,
                            tint = cyan500.copy(alpha = 0.3f),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Text(
                            text = placeLiveData?.playtime ?: "영업시간 없음",
                            style = typography.h6.copy(fontSize = 12.sp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        FaIcon(
                            faIcon = FaIcons.Globe,
                            tint = cyan500.copy(alpha = 0.3f),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Text(
                            text = placeLiveData?.url ?: "홈페이지 없음",
                            style = typography.h6.copy(fontSize = 12.sp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        FaIcon(
                            faIcon = FaIcons.Phone,
                            tint = cyan500.copy(alpha = 0.3f),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Text(
                            text = placeLiveData?.tel ?: "전화번호 없음",
                            style = typography.h6.copy(fontSize = 12.sp)
                        )
                    }

                }
                item {
                    Text(
                        text = "사용자가 올린사진",
                        style = typography.caption,
                        color = typography.caption.color.copy(alpha = 0.5f)
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        if(placeLiveData?.contentid != null)
                            PlanGramImageGrid(placeLiveData?.contentid!!,100.dp)
                    }
                }
                item {
                    Text(
                        text = "카카오 리뷰",
                        style = typography.caption,
                        color = typography.caption.color.copy(alpha = 0.5f)
                    )
                }
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    FaIcon(
                                        faIcon = FaIcons.Star,
                                        size = 10.dp,
                                        tint = yellow
                                    )
                                    Text(text = "별점", fontSize = 10.sp)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewPlaceDetail(){
//    PlaceDetail()
}