package com.aamu.aamuandroidapp.components.aamuplan.PlanDetails

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Shapes
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModelFactory
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanListVerticalItem
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanListWidthItem
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanMove
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.amber700
import com.aamu.aamuandroidapp.ui.theme.components.Material3Card
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.orange200
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanDetails(
    context : Context,
    mapviewModel : AAMUPlanViewModel,
    modifierBottom : Modifier,
    modifierBottomStart : Modifier,
    scaffoldState : BottomSheetScaffoldState,
    topbarhide : MutableState<Boolean>,
    startMove : MutableState<Boolean>,
    coroutineScope: CoroutineScope
){
    val plannerSelectOne by mapviewModel.plannerSelectOne.observeAsState()
    PlanListScroll(mapviewModel, scaffoldState, coroutineScope,topbarhide, startMove)
    if(topbarhide.value && scaffoldState.drawerState.isClosed && !startMove.value) {
        Surface(elevation = 5.dp, modifier = modifierBottomStart.size(64.dp)) {
            Button(onClick = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }, colors = ButtonDefaults.buttonColors(Color.White)) {
                Icon(Icons.Filled.ArrowForward, null)
            }
        }
    }
    BackHandler(topbarhide.value) {
        topbarhide.value = false
        mapviewModel.setCurrentMarker()
    }
    BackHandler(scaffoldState.drawerState.isOpen) {
        coroutineScope.launch {
            scaffoldState.drawerState.close()
        }
    }
    BackHandler(startMove.value) {
        startMove.value = false
    }

    AnimatedVisibility(visible = startMove.value, modifier = modifierBottom,
        enter = slideIn(tween(1000, easing = LinearOutSlowInEasing)){ fullSize->
            IntOffset(0, fullSize.height)
        }, exit = slideOut(tween(1000, easing = LinearOutSlowInEasing)){ fullSize ->
            IntOffset(0, fullSize.height)
        }){
        Box(modifier = modifierBottom
            .padding(bottom = 50.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .height(120.dp)){
            PlanMove(context,mapviewModel)
        }
    }
    AnimatedVisibility(visible = topbarhide.value, enter = slideInVertically(), exit = slideOutVertically(targetOffsetY = {-2000})) {
        Surface(elevation = 1.dp) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding() + 56.dp
                    )
                    .background(color = amber700),
            )
            Row(
                modifier = Modifier
                    .padding(start = 3.dp)
                    .statusBarsPadding(), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                    topbarhide.value = false
                    startMove.value = false
                    mapviewModel.setCurrentMarker()
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
                Text(text = plannerSelectOne?.title ?: "", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanListScroll(
    mapviewModel : AAMUPlanViewModel,
    scaffoldState : BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    topbarhide : MutableState<Boolean>,
    startMove : MutableState<Boolean>
){
    val planners by mapviewModel.planners.observeAsState(emptyList())
    val plannerSelectOne by mapviewModel.plannerSelectOne.observeAsState()

    val isSelectOne by mapviewModel.isSelectOne.observeAsState()

    val lazyListState = rememberLazyListState()
    val lazyListStateVertical = rememberLazyListState()
    var selectedIndex by remember{mutableStateOf(-1)}
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                coroutineScope.launch {
                    lazyListState.scrollToItem(lazyListState.firstVisibleItemIndex)
                    lazyListStateVertical.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                }
                return super.onPreScroll(available, source)
            }
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                coroutineScope.launch {
                    lazyListStateVertical.scrollToItem(lazyListState.firstVisibleItemIndex,lazyListState.firstVisibleItemScrollOffset)

                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    if(isSelectOne == true){
        coroutineScope.launch {
            lazyListStateVertical.scrollToItem(0)
            lazyListState.scrollToItem(0)
        }
        mapviewModel.isSelectOne.value = false
    }

    var mapMakerIndex : Int = if(lazyListStateVertical.firstVisibleItemIndex-1 >= 0) lazyListStateVertical.firstVisibleItemIndex-1 else 0
    if(lazyListStateVertical.firstVisibleItemIndex == 0){
        mapviewModel.setCurrentMarker()
    }
    else if (planners.size != 0
        && planners.get(mapMakerIndex) != null
        && !startMove.value
        && topbarhide.value
    ) {
        if (planners[mapMakerIndex].dto?.mapx != null
            && planners[mapMakerIndex].dto?.mapy != null
        ) {
            planners[mapMakerIndex].dto?.let {
                mapviewModel.setMarker(
                    it
                )
            }
        } else {
            mapviewModel.setDayMarker(planners[mapMakerIndex].dto?.title!!)
        }
    }
    AnimatedVisibility(visible = topbarhide.value&& scaffoldState.drawerState.isClosed && !startMove.value,
        enter = slideInHorizontally(), exit = slideOutHorizontally(targetOffsetX = {(-200)})) {
        BoxWithConstraints {
            Surface(elevation = 3.dp) {
                LazyColumn(
                    modifier = Modifier
                        .width(64.dp)
                        .padding(
                            top = WindowInsets.statusBars
                                .asPaddingValues()
                                .calculateTopPadding() + 164.dp
                        )
                        .nestedScroll(nestedScrollConnection)
                        .background(Color.White),
                    state = lazyListState,
                    contentPadding = PaddingValues(5.dp)
                ) {
                    itemsIndexed(items = planners,
                        itemContent = { index, planner ->
                            Spacer(modifier = Modifier.padding(bottom = 5.dp))
                            PlanListVerticalItem(planner, index,selectedIndex,lazyListState,lazyListStateVertical,coroutineScope)
                            Spacer(modifier = Modifier.padding(bottom = 5.dp))
                        }
                    )
                    item {
                        Spacer(modifier = Modifier.padding(top = maxHeight - 50.dp))
                    }
                }
            }
        }

    }

    AnimatedVisibility(visible =topbarhide.value && scaffoldState.drawerState.isClosed && !startMove.value,
        enter = slideInVertically(), exit = slideOutVertically(targetOffsetY = {-2000})) {
        Surface(elevation = 3.dp) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding() + 56.dp
                    )
                    .height(100.dp)
                    .nestedScroll(nestedScrollConnection)
                    .background(Color.White),
                state = lazyListStateVertical,
                contentPadding = PaddingValues(5.dp),
                userScrollEnabled = false
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(91.dp),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
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
                    }
                    Spacer(modifier = Modifier.padding(bottom = 5.dp))

                }
                itemsIndexed(items = planners,
                    itemContent = { index, planner ->
                        PlanListWidthItem(mapviewModel,planner, index,startMove)
                        Spacer(modifier = Modifier.padding(bottom = 5.dp))
                    }
                )
            }
        }
    }

}