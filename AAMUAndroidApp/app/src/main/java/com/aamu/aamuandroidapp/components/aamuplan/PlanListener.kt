package com.aamu.aamuandroidapp.components.aamuplan

import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView

class PlanListener : MapView.CurrentLocationEventListener,MapReverseGeoCoder.ReverseGeoCodingResultListener {
    override fun onCurrentLocationUpdate(mapView: MapView?, currentLocation: MapPoint?, accuracyInMeters: Float) {

    }

    override fun onCurrentLocationDeviceHeadingUpdate(mapView: MapView?, v: Float) {

    }

    override fun onCurrentLocationUpdateFailed(mapView: MapView?) {

    }

    override fun onCurrentLocationUpdateCancelled(mapView: MapView?) {

    }

    override fun onReverseGeoCoderFoundAddress(mapReverseGeoCoder: MapReverseGeoCoder?, s: String?) {

    }

    override fun onReverseGeoCoderFailedToFindAddress(mapReverseGeoCoder: MapReverseGeoCoder?) {

    }
}