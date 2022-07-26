package com.aamu.aamuandroidapp.components.aamuplan

import android.content.Context
import android.location.Location
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
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
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.fragment.main.planner.PlannerFragment
import com.aamu.aamuandroidapp.util.getLatLng
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import net.daum.mf.map.api.*

class AAMUPlanViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUPlanViewModel(context) as T
    }
}

class AAMUPlanViewModel(context : Context) : ViewModel(), MapView.POIItemEventListener{

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()
    private val context = context
    val mapView = MapView(context)

    val recentPlaces = MutableLiveData<List<AAMUPlaceResponse>>()
    val errorLiveData = MutableLiveData<String>()
    val plannerSelectList = MutableLiveData<List<AAMUPlannerSelectOne>>()
    val plannerSelectOne = MutableLiveData<AAMUPlannerSelectOne>()
    val planners = MutableLiveData<MutableList<Place>>()
    val place = MutableLiveData<Place>()

    init {
        setCurrentMarker()
        viewModelScope.launch {
            aamuRepository.getPlannerSelectList()
                .collect{aamuListPlanner->
                    if (aamuListPlanner.isNotEmpty()){
                        plannerSelectList.value = aamuListPlanner
                    }
                    else{
                        errorLiveData.value = "리스트를 받아오는데 실페했습니다"
                        Toast.makeText(context,"리스트를 받아오는데 실페했습니다",Toast.LENGTH_LONG)
                    }
            }
        }

    }

    fun setCurrentMarker(){
        val location : Location = getLatLng()
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude),true)
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        val mCustomMarker :MapPOIItem =MapPOIItem()
        mCustomMarker.itemName = "현재 위치"
        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude)
        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage

        mCustomMarker.customImageResourceId = R.drawable.map_start_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker);
        mapView.setZoomLevel(5, true)
    }

    fun getRcentPlaces() = viewModelScope.launch {
        aamuRepository.getRecentPlace(
            mapView.mapCenterPoint.mapPointGeoCoord.latitude,
            mapView.mapCenterPoint.mapPointGeoCoord.longitude)
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

    fun getPlannerSelectOne(rbn : Int) = viewModelScope.launch {
        aamuRepository.getPlannerSelectOne(rbn)
            .collect {selectOne ->
                if (selectOne.rbn != null){
                    plannerSelectOne.value = selectOne
                    selectOne.let {
                        val keys = it.routeMap?.keys
                        val tempPlanner = ArrayList<Place>(emptyList())
                        for (key in keys!!){
                            tempPlanner.add(Place(dto = AAMUPlaceResponse(title = "${key}")))
                            for( place in it.routeMap!!.get(key)!!){
                                tempPlanner.add(place!!)
                            }
                        }
                        planners.value = tempPlanner
                    }
                }
                else{
                    errorLiveData.value = "Failed to PlannerSelectOne"
                    Toast.makeText(context,"Failed to PlannerSelectOne",Toast.LENGTH_LONG)
                }
            }
    }

    fun setDayMarker(title : String){
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        val mCustomMarker : MapPOIItem = MapPOIItem()
        val mapPoint : MutableList<MapPoint> = emptyList<MapPoint>().toMutableList()
        for(place in plannerSelectOne.value?.routeMap?.get(title)!!){
            mCustomMarker.itemName = place?.dto?.title
            mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(place?.dto?.mapy!!,place?.dto?.mapx!!)
            mapPoint.add(mCustomMarker.mapPoint)
            mCustomMarker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround

            mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage
            mCustomMarker.customImageResourceId = R.drawable.map_end_icon
            mCustomMarker.setCustomImageAutoscale(false)

            mapView.addPOIItem(mCustomMarker)
        }
        val polyLine : MapPolyline = MapPolyline()
        polyLine.addPoints(mapPoint.toTypedArray())
        polyLine.lineColor = Color(0xAAf44336).hashCode()
        mapView.addPolyline(polyLine)

        mapView.fitMapViewAreaToShowMapPoints(mapPoint.toTypedArray())
        mapView.zoomOut(true)
    }


    fun setMarker(title : String , contentid : Int , mapy : Double , mapx : Double){
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        mapView.setZoomLevel(5, true)
        val mCustomMarker :MapPOIItem =MapPOIItem()
        mCustomMarker.itemName = title
        mCustomMarker.tag = contentid
        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(mapy,mapx)
        mCustomMarker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround

        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage
        mCustomMarker.customImageResourceId = R.drawable.map_end_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker)
        mapView.selectPOIItem(mCustomMarker, false)
        mapView.currentLocationTrackingMode=MapView.CurrentLocationTrackingMode.TrackingModeOff
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mapy,mapx), true)
    }


    fun getPlanMove(planner: Place){
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        val location : Location = getLatLng()

        val mapPoint : Array<MapPoint> = arrayOf(MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude)
                ,MapPoint.mapPointWithGeoCoord(planner.dto?.mapy!!,planner.dto?.mapx!!))

        val mCustomMarker :MapPOIItem =MapPOIItem()

        mCustomMarker.itemName = "현재 위치"
        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude)
        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage

        mCustomMarker.customImageResourceId = R.drawable.map_start_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker);

        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(planner.dto?.mapy!!,planner.dto?.mapx!!)
        mCustomMarker.itemName = planner.dto?.title
        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage

        mCustomMarker.customImageResourceId = R.drawable.map_end_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker);
        val polyLine : MapPolyline = MapPolyline()
        polyLine.addPoints(mapPoint)
        polyLine.lineColor = Color(0xAAf44336).hashCode()
        mapView.addPolyline(polyLine)
        mapView.fitMapViewAreaToShowMapPoints(mapPoint)
        mapView.zoomOut(true)
        place.value = planner
    }

    override fun onPOIItemSelected(mapView: MapView?, mapPOIItem: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, mapPOIItem: MapPOIItem?) {}

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        mapPOIItem: MapPOIItem?,
        calloutBalloonButtonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
        TODO("Not yet implemented")
    }

    override fun onDraggablePOIItemMoved(mapView: MapView?, mapPOIItem: MapPOIItem?, mapPoint: MapPoint?) {
        TODO("Not yet implemented")
    }
}

class CustomCalloutBalloonAdapter : CalloutBalloonAdapter{

    private lateinit var mCalloutBallon : ComposeView

    public fun CustomCalloutBalloonAdapter(){
        mCalloutBallon = ComposeView(PlannerFragment().requireContext())
    }

    override fun getCalloutBalloon(mapPOIItem: MapPOIItem?): View {
        return mCalloutBallon.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

            }
        }
    }

    override fun getPressedCalloutBalloon(mapPOIItem: MapPOIItem?): View? {
        return null
    }

}