package com.aamu.aamuandroidapp.components.aamuplan

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AAMUPlanHome(){
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
//            ExtendedFloatingActionButton(
//                text = {},
//                icon = {
//                    androidx.compose.material3.Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = null,
//                        modifier = Modifier.padding(horizontal = 8.dp)
//                    )
//                },
//                onClick = {}
//            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                val map = mapView()
                KakaoMap(mapView = map, latitude = "1.3521", longitude = "103.8198", PlanListener())
            }
        }
    )
}

@Composable
fun mapView(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context)
    }
    return mapView
}

@Composable
fun KakaoMap(
    mapView: MapView,
    latitude: String,
    longitude: String,
    eventListener: PlanListener
) {
    AndroidView({ mapView })
    {mapView->
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude.toDouble(), longitude.toDouble()), true)
        mapView.setZoomLevel(3, true)
        mapView.setCurrentLocationEventListener(eventListener)
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading)
    }
}

@Preview
@Composable
fun PreviewAAMUPlanHome() {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        AAMUPlanHome()
    }
}

