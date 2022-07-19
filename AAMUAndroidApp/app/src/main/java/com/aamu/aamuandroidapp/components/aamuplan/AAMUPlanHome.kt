package com.aamu.aamuandroidapp.components.aamuplan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AAMUPlanHome(){
//    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
//    val mapviewModel : AAMUPlanViewModel = viewModel(
//        factory = AAMUPlanViewModelFactory(LocalContext.current)
//    )
    BottomSheetScaffold(
        modifier = Modifier.shadow(0.dp),
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        content = {
            Box(contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.fillMaxSize()) {
//                    KakaoMap(mapView = mapviewModel.mapView, PlanListener())
                }
                FloatingActionButton(onClick = {
//                    mapviewModel.mapView.currentLocationTrackingMode=MapView.CurrentLocationTrackingMode.TrackingModeOff
//                    mapviewModel.getRcentPlaces()

                }, modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(width = 50.dp, height = 50.dp)
                    .offset(x = (-10).dp, y = 30.dp)
                    , containerColor = Color.White, shape = Shapes.Full) {
                    Row() {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        sheetContent = {
            PlanBottomSheet()
        },
//        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 25.dp
    )
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

