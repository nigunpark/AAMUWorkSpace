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
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.ui.theme.amber700
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanDetails(
//    mapviewModel : AAMUPlanViewModel,
    scaffoldState : BottomSheetScaffoldState,
    topbarhide : MutableState<Boolean>,
    coroutineScope: CoroutineScope
){
    PlanListScroll(scaffoldState,coroutineScope)
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
            }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
            Text(text = "ㅎㅇㅎ")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanListScroll(
    scaffoldState : BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
){
    val mapviewModel : AAMUPlanViewModel = viewModel(
        factory = AAMUPlanViewModelFactory(LocalContext.current)
    )

    val planners by mapviewModel.planners.observeAsState(emptyList())
    val plannerSelectOne by mapviewModel.plannerSelectOne.observeAsState()

    val lazyListState = rememberLazyListState()
    val lazyListState2 = rememberLazyListState()
    var selectedIndex by remember{mutableStateOf(-1)}
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                coroutineScope.launch {
                    lazyListState.scrollToItem(lazyListState.firstVisibleItemIndex)
                    lazyListState2.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                }
                return super.onPreScroll(available, source)
            }
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                coroutineScope.launch {
                    lazyListState2.scrollToItem(lazyListState.firstVisibleItemIndex,lazyListState.firstVisibleItemScrollOffset)

                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }
    var mapMakerIndex : Int = if(lazyListState2.firstVisibleItemIndex-1 >= 0) lazyListState2.firstVisibleItemIndex-1 else 0
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

    if(scaffoldState.drawerState.isClosed) {
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
                    contentPadding = PaddingValues(5.dp),
                    state = lazyListState,
                ) {
                    itemsIndexed(items = planners,
                        itemContent = { index, planner ->
                            PlanListVerticalItem(planner, index,selectedIndex,lazyListState,lazyListState2,coroutineScope)
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
                state = lazyListState2,
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
                }
                itemsIndexed(items = planners,
                    itemContent = { index, planner ->
                        PlanListWidthItem(planner, index)
                    }
                )
            }
        }
    }
}