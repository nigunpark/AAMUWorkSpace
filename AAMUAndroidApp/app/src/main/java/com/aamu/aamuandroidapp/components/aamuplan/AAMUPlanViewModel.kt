package com.aamu.aamuandroidapp.components.aamuplan

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aamu.aamuandroidapp.R
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class AAMUPlanViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUPlanViewModel(context) as T
    }
}

class AAMUPlanViewModel(context : Context) : ViewModel(){
    val mapView = MapView(context)
    init {
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord("103.8198".toDouble(), "1.3521".toDouble()), true)
        mapView.setZoomLevel(3, true)
    }

    fun setMarker(){
        val mCustomMarker :MapPOIItem =MapPOIItem()
        val name : String = "Custom Marker"
        mCustomMarker.itemName = name
        mCustomMarker.tag = 1
        mCustomMarker.mapPoint = mapView.mapCenterPoint
        mCustomMarker.showAnimationType = MapPOIItem.ShowAnimationType.DropFromHeaven
        mCustomMarker.markerType = MapPOIItem.MarkerType.RedPin

        mapView.addPOIItem(mCustomMarker)
        mapView.selectPOIItem(mCustomMarker, true)
        mapView.setMapCenterPoint(mapView.mapCenterPoint, true)
    }
}