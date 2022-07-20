package com.aamu.aamuandroidapp.components.aamuplan

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.components.carousel.Pager
import com.aamu.aamuandroidapp.components.carousel.PagerState
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.amber500
import com.aamu.aamuandroidapp.ui.theme.orange700
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AAMUPlanHome(){
    val context : Context = LocalContext.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val lazyListState = rememberLazyListState()
    val lazyListState2 = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                    lazyListState2.animateScrollToItem(if(lazyListState.firstVisibleItemIndex-1<0) 0 else lazyListState.firstVisibleItemIndex-1)
                }
                return Offset.Zero
            }
        }
    }


//    val mapviewModel : AAMUPlanViewModel = viewModel(
//        factory = AAMUPlanViewModelFactory(LocalContext.current)
//    )
    var topbarhide = remember { mutableStateOf(false) }
    var drawerArrowDrawable = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()){
        BottomSheetScaffold(
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            content = {
                Box(contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.fillMaxSize()) {
//                        KakaoMap(mapView = mapviewModel.mapView, PlanListener())
                    }
                    if(!topbarhide.value) {
//                    ActionButton(mapviewModel,
//                        Modifier
//                            .statusBarsPadding()
//                            .align(Alignment.TopEnd),
//                        topbarhide,
//                        bottomSheetScaffoldState,
//                        coroutineScope)
                        ActionButton(
                            Modifier
                                .statusBarsPadding()
                                .align(Alignment.TopEnd),
                            topbarhide,
                            bottomSheetScaffoldState,
                            coroutineScope)
                    }
                }
            },
            sheetContent = {
                PlanBottomSheet()
            },
            drawerBackgroundColor = orange700,
            drawerGesturesEnabled = true,
            drawerContent = {
                Spacer(modifier = Modifier.height(56.dp))
                SideContent() },
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 25.dp,

        )
        val lists : MutableList<Int> = arrayListOf()
        for(i : Int in 1..50)
            lists.add(i)
        if(topbarhide.value) {
            if(bottomSheetScaffoldState.drawerState.isClosed) {
                BoxWithConstraints {
                    LazyColumn(
                        modifier = Modifier
                            .width(70.dp)
                            .background(color = amber200)
                            .padding(top = 112.dp)
                            .nestedScroll(nestedScrollConnection),
                        state = lazyListState
                    ) {
                        itemsIndexed(items = lists,
                            itemContent = { index, list ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    androidx.compose.material.Text(text = "Item " + list.toString())
                                }
                            }
                        )
                        item {
                            Spacer(modifier = Modifier.padding(top = maxHeight-70.dp))
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp)
                        .height(56.dp)
                        .background(amber500)
                        .nestedScroll(nestedScrollConnection),
                    state = lazyListState2
                ) {
                    itemsIndexed(items = lists,
                        itemContent = { index, list ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                androidx.compose.material.Text("Item : " + list.toString())
                            }
                        }
                    )
                }


//                Pager(state = pagerState,
//                    orientation = Orientation.Vertical,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(112.dp).background(amber500)) {
//                    val list=lists[commingPage]
//                    val isSelected = pagerState.currentPage == commingPage
//
//                }
            }
            PlanDetails(bottomSheetScaffoldState,topbarhide,coroutineScope)
        }
    }

}




@Composable
fun KakaoMap(
    mapView: MapView,
    eventListener: PlanListener
) {
    AndroidView({ mapView })
    {mapView->
        mapView.setCurrentLocationEventListener(eventListener)
        mapView.currentLocationTrackingMode=MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }
}

@Preview
@Composable
fun PreviewAAMUPlanHome() {
        AAMUPlanHome()
}

@Composable
fun PlanBottomSheet(){
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(Color.White)
    ) {
        androidx.compose.material.Divider(
            color = Color.Gray,
            thickness = 5.dp,
            modifier = Modifier
                .padding(top = 15.dp)
                .align(Alignment.TopCenter)
                .width(80.dp)
                .clip(RoundedCornerShape(50.dp))
        )
        Box(
            Modifier
                .padding(top = 25.dp)
                .height(300.dp)) {
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionButton(
//    mapviewModel : AAMUPlanViewModel,
    modifier: Modifier,
    topbarhide : MutableState<Boolean>,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
){
    Box(modifier = modifier) {
        FloatingActionButton(
            onClick = {
//                mapviewModel.mapView.currentLocationTrackingMode=MapView.CurrentLocationTrackingMode.TrackingModeOff
//                mapviewModel.getRcentPlaces()
                topbarhide.value = true
                coroutineScope.launch { bottomSheetScaffoldState.drawerState.open() }
            },
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .offset(x = (-20).dp, y = 5.dp),
            containerColor = Color.White,
            shape = Shapes.Full
        ) {
            Row() {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanDetails(
//    mapviewModel : AAMUPlanViewModel,
    scaffoldState : BottomSheetScaffoldState,
    topbarhide : MutableState<Boolean>,
    coroutineScope: CoroutineScope
){
    Column() {
        Spacer(modifier = Modifier.statusBarsPadding())
        TopAppBar() {
            IconButton(onClick = {
                coroutineScope.launch { scaffoldState.drawerState.close() }
                topbarhide.value = false
            }) {
                androidx.compose.material.Icon(Icons.Filled.ArrowBack, null)
            }
            androidx.compose.material.Text(text = "ㅎㅇㅎ")
        }
    }
}

@Composable
fun SideContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        androidx.compose.material.Text(text = "Item 1")
        androidx.compose.material.Icon(imageVector = Icons.Default.List, contentDescription = "List")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        androidx.compose.material.Text(text = "Item 2")
        androidx.compose.material.Icon(imageVector = Icons.Default.List, contentDescription = "List")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        androidx.compose.material.Text(text = "Item 3")
        androidx.compose.material.Icon(imageVector = Icons.Default.List, contentDescription = "List")
    }
}

