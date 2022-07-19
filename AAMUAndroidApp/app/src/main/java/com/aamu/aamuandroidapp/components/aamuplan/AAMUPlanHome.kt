package com.aamu.aamuandroidapp.components.aamuplan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.orange700
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AAMUPlanHome(){
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
//    val mapviewModel : AAMUPlanViewModel = viewModel(
//        factory = AAMUPlanViewModelFactory(LocalContext.current)
//    )
    var topbarhide = remember { mutableStateOf(false) }
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
            drawerGesturesEnabled = topbarhide.value,
            drawerContent = {
                Spacer(modifier = Modifier.height(56.dp))
                SideContent() },
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 25.dp
        )
        val lists : MutableList<Int> = arrayListOf()
        for(i : Int in 1..50)
            lists.add(i)
        if(topbarhide.value) {
            if(bottomSheetScaffoldState.drawerState.isClosed) {
                LazyColumn(
                    modifier = Modifier
                        .width(70.dp)
                        .background(color = amber200)
                        .padding(top = 56.dp),
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
                }
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

