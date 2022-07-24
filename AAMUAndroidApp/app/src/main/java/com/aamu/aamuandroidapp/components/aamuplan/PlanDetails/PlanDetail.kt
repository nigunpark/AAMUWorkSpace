package com.aamu.aamuandroidapp.components.aamuplan.PlanDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModelFactory
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanListVerticalItem
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanListWidthItem
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanMove
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.ui.theme.amber700
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanDetails(
    mapviewModel : AAMUPlanViewModel,
    modifierBottom : Modifier,
    scaffoldState : BottomSheetScaffoldState,
    topbarhide : MutableState<Boolean>,
    startMove : MutableState<Boolean>,
    coroutineScope: CoroutineScope
){
    val plannerSelectOne by mapviewModel.plannerSelectOne.observeAsState()

    if(scaffoldState.drawerState.isClosed && !startMove.value) {
        PlanListScroll(mapviewModel, coroutineScope, startMove)
    }
    if(startMove.value){
        Box(modifier = modifierBottom
            .padding(bottom = 50.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .height(120.dp)){
            PlanMove(mapviewModel)
        }
    }
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
        Row(modifier = Modifier
            .padding(start = 3.dp)
            .statusBarsPadding()
            , verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                coroutineScope.launch { scaffoldState.drawerState.close() }
                topbarhide.value = false
                startMove.value = false
            }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
            Text(text = plannerSelectOne?.title?:"")
        }
    }
}

@Composable
fun PlanListScroll(
    mapviewModel : AAMUPlanViewModel,
    coroutineScope: CoroutineScope,
    startMove : MutableState<Boolean>
){
    val planners by mapviewModel.planners.observeAsState(emptyList())

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
    var mapMakerIndex : Int = if(lazyListStateVertical.firstVisibleItemIndex-1 >= 0) lazyListStateVertical.firstVisibleItemIndex-1 else 0
    if (planners.size != 0
        && planners.get(mapMakerIndex) != null) {
        if (planners[mapMakerIndex].dto?.mapx != null
            && planners[mapMakerIndex].dto?.mapy != null
        ) {
            mapviewModel.setMarker(
                planners[mapMakerIndex].dto?.title!!,
                planners[mapMakerIndex].dto?.contentid!!,
                planners[mapMakerIndex].dto?.mapy!!,
                planners[mapMakerIndex].dto?.mapx!!
            )
        }
    }

    BoxWithConstraints {
        Surface(elevation = 3.dp) {
            LazyColumn(
                modifier = Modifier
                    .width(64.dp)
                    .padding(
                        top = WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding() + 112.dp
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
    Surface(elevation = 3.dp) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding() + 56.dp
                )
                .height(64.dp)
                .nestedScroll(nestedScrollConnection)
                .background(Color.White),
            state = lazyListStateVertical,
            contentPadding = PaddingValues(5.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    Arrangement.Center,
                    Alignment.CenterVertically
                ) {
                    Text(text = "여행출발하기")
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