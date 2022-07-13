package com.aamu.aamuandroidapp.components.aamuplan

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@Composable
fun AAMUPlanHome(){
    val map = mapView()
    KakaoMap(mapView = map, latitude = "1.3521", longitude = "103.8198",PlanListener())
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
