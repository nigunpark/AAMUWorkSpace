package com.aamu.aamuandroidapp.components.aamuplan

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.fragment.main.planner.PlannerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class AAMUPlanViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUPlanViewModel(context) as T
    }
}

class AAMUPlanViewModel(context : Context) : ViewModel(){

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()
    private val context = context
    val mapView = MapView(context)

    val recentPlaces = MutableLiveData<List<AAMUPlaceResponse>>()
    val errorLiveData = MutableLiveData<String>()
    init {
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord("103.8198".toDouble(), "1.3521".toDouble()), true)
        mapView.setZoomLevel(5, true)
    }

    fun getRcentPlaces() = viewModelScope.launch {
        aamuRepository.getRecentDiner(
            mapView.mapCenterPoint.mapPointGeoCoord
                .latitude,mapView.mapCenterPoint.mapPointGeoCoord.longitude)
            .collect { aamuplaces ->
                if (aamuplaces.isNotEmpty()){
                    recentPlaces.value = aamuplaces
                    mapView.removeAllPOIItems()
                }
                else{
                    errorLiveData.value = "Failed to Places"
                    Toast.makeText(context,"Failed to Places",Toast.LENGTH_LONG)
                }
            }
        recentPlaces.value?.let {
            for (recentPlace in it){
                val mCustomMarker :MapPOIItem =MapPOIItem()
                mCustomMarker.itemName = recentPlace.title
                mCustomMarker.tag = recentPlace.contentid!!
                mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(recentPlace.mapy!!,recentPlace.mapx!!)
                mCustomMarker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
                mCustomMarker.markerType = MapPOIItem.MarkerType.RedPin

                mapView.addPOIItem(mCustomMarker)
                mapView.selectPOIItem(mCustomMarker, true)
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(recentPlace.mapy!!,recentPlace.mapx!!), true)
            }
        }
    }

//    fun setMarker() {
//
//        val mCustomMarker :MapPOIItem =MapPOIItem()
//        val name : String = "Custom Marker"
//        mCustomMarker.itemName = name
//        mCustomMarker.tag = 1
//        mCustomMarker.mapPoint = mapView.mapCenterPoint
//        mCustomMarker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
//        mCustomMarker.markerType = MapPOIItem.MarkerType.RedPin
//
//        mapView.addPOIItem(mCustomMarker)
//        mapView.selectPOIItem(mCustomMarker, true)
//        mapView.setMapCenterPoint(mapView.mapCenterPoint, true)
//    }
}

class CustomCalloutBalloonAdapter : CalloutBalloonAdapter{

    private lateinit var mCalloutBallon : ComposeView

    public fun CustomCalloutBalloonAdapter(){
        mCalloutBallon = ComposeView(PlannerFragment().requireContext())
    }

    override fun getCalloutBalloon(p0: MapPOIItem?): View {
        return mCalloutBallon.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

            }
        }
    }

    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View? {
        return null
    }

}