package com.aamu.aamuandroidapp.components.aamuplan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.*
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.Place
import com.aamu.aamuandroidapp.util.getLatLng
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import kotlinx.coroutines.launch
import net.daum.mf.map.api.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class AAMUPlanViewModelFactory(val context: Context,val navController : NavController) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUPlanViewModel(context,navController) as T
    }
}

class AAMUPlanViewModel(context : Context,navController : NavController) : ViewModel(), MapView.POIItemEventListener{

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()
    private val context = context
    lateinit var mapView : MapView

    val viewModelnavController = navController

    val recentPlaces = MutableLiveData<List<AAMUPlaceResponse>>()

    val errorLiveData = MutableLiveData<String>()

    val plannerSelectList = MutableLiveData<List<AAMUPlannerSelectOne>>()

    val plannerSelectOne = MutableLiveData<AAMUPlannerSelectOne>()
    val isSelectOne = MutableLiveData<Boolean>(false)

    val planners = MutableLiveData<MutableList<Place>>()
    val place = MutableLiveData<Place>()

    val detailPlace = MutableLiveData<AAMUPlaceResponse>()
    val isPlaceDetail = MutableLiveData<Boolean>(false)

    val mapPOIItems = MutableLiveData<MapPOIItem>()

    fun getPlannerSelectList(){
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

    fun setInitMap(){
        mapView.setCurrentLocationEventListener(PlanListener())
        mapView.setCalloutBalloonAdapter(CustomCalloutBalloonAdapter(context))
        mapView.setPOIItemEventListener(this)
        setCurrentMarker()
    }

    fun setPlanDetailCenter(place : AAMUPlaceResponse){
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(place.mapy!! - 0.05,place.mapx!!),true)
        val mCustomMarker :MapPOIItem =MapPOIItem()
        mCustomMarker.itemName = place.title
        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(place.mapy!!,place.mapx!!)
        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage

        mCustomMarker.customImageResourceId = R.drawable.map_end_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker)
        mapView.setZoomLevel(5, true)
    }

    fun setCurrentMarker(){
        val location : Location? = getLatLng()
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location?.latitude ?: 33.450701, location?.longitude ?: 126.570667),true)
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        val mCustomMarker :MapPOIItem =MapPOIItem()
        mCustomMarker.itemName = "현재 위치"
        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(location?.latitude ?:33.450701,location?.longitude ?:126.570667)
        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage

        mCustomMarker.customImageResourceId = R.drawable.map_start_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker);
        mapView.setZoomLevel(5, true)
    }

    fun getRcentPlaces() = viewModelScope.launch {
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        val location : Location? = getLatLng()
        aamuRepository.getRecentPlace(location?.latitude ?:33.450701,location?.longitude ?:126.570667)
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
                mCustomMarker.userObject = recentPlace
                mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(recentPlace.mapy!!,recentPlace.mapx!!)
                mCustomMarker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
                mCustomMarker.markerType = MapPOIItem.MarkerType.RedPin

                mapView.addPOIItem(mCustomMarker)
                mapView.selectPOIItem(mCustomMarker, true)
            }
        }
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location?.latitude ?:33.450701,location?.longitude ?:126.570667), true)
    }

    fun getPlannerSelectOne(rbn : Int) = viewModelScope.launch {
        isSelectOne.value = true
        aamuRepository.getPlannerSelectOne(rbn)
            .collect {selectOne ->
                if (selectOne.rbn != null){
                    plannerSelectOne.value = selectOne
                    selectOne.let {
                        val keys = it.routeMap?.keys
                        val tempPlanner = ArrayList<Place>(emptyList())
                        for (key in keys!!){
                            val tempPlace = Place(dto = AAMUPlaceResponse(title = "${key}"))
                            val tempString = ArrayList<String>(emptyList())
                            tempPlanner.add(tempPlace)
                            for( place in it.routeMap!!.get(key)!!){
                                tempPlanner.add(place!!)
                                tempString.add(place.dto?.title!!)
                            }
                            tempPlace.dto?.addr = tempString.joinToString("&&")
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
            mCustomMarker.userObject = place?.dto
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
        polyLine.lineColor = Color(0xAAf44336).toArgb()
        mapView.addPolyline(polyLine)

        mapView.fitMapViewAreaToShowMapPoints(mapPoint.toTypedArray())
        mapView.zoomOut(true)
    }


    fun setMarker(place : AAMUPlaceResponse){
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        mapView.setZoomLevel(5, true)
        val mCustomMarker :MapPOIItem =MapPOIItem()
        mCustomMarker.itemName = place.title
        mCustomMarker.tag = place.contentid!!
        mCustomMarker.userObject = place
        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(place.mapy!!,place.mapx!!)
        mCustomMarker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround

        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage
        mCustomMarker.customImageResourceId = R.drawable.map_end_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker)
        mapView.selectPOIItem(mCustomMarker, false)
        mapView.currentLocationTrackingMode=MapView.CurrentLocationTrackingMode.TrackingModeOff
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(place.mapy!!,place.mapx!!), true)
    }


    fun getPlanMove(planner: Place){
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
        val location : Location? = getLatLng()

        val mapPoint : Array<MapPoint> = arrayOf(MapPoint.mapPointWithGeoCoord(location?.latitude ?: 33.450701, location?.longitude ?: 126.570667)
                ,MapPoint.mapPointWithGeoCoord(planner.dto?.mapy!!,planner.dto?.mapx!!))

        val mCustomMarker :MapPOIItem =MapPOIItem()

        mCustomMarker.itemName = "현재 위치"
        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(location?.latitude ?: 33.450701, location?.longitude ?: 126.570667)
        mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage

        mCustomMarker.customImageResourceId = R.drawable.map_start_icon
        mCustomMarker.setCustomImageAutoscale(false)

        mapView.addPOIItem(mCustomMarker);

        mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(planner.dto?.mapy!!,planner.dto?.mapx!!)
        mCustomMarker.itemName = planner.dto?.title
        mCustomMarker.userObject = planner.dto
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

    override fun onPOIItemSelected(mapView: MapView?, mapPOIItem: MapPOIItem?) {}

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, mapPOIItem: MapPOIItem?) {}

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        mapPOIItem: MapPOIItem?,
        calloutBalloonButtonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
        if(mapPOIItem?.userObject != null) {
            val places: AAMUPlaceResponse = mapPOIItem?.userObject as AAMUPlaceResponse
            detailPlace.value = places
            isPlaceDetail.value = true
//            val action =PlannerFragmentDirections.actionPlannerFragmentToPlaceDetailFragment(place)
//            viewModelnavController.navigate(action)
        }
    }

    override fun onDraggablePOIItemMoved(mapView: MapView?, mapPOIItem: MapPOIItem?, mapPoint: MapPoint?) {}
}

class CustomCalloutBalloonAdapter(context: Context) : CalloutBalloonAdapter{

    private var mCalloutBallon : View

    init {
        mCalloutBallon = LayoutInflater.from(context).inflate(R.layout.custom_callout_balloon,null)
    }

    override fun getCalloutBalloon(mapPOIItem: MapPOIItem?): View {
        if(mapPOIItem?.userObject != null) {
            val place: AAMUPlaceResponse = mapPOIItem?.userObject as AAMUPlaceResponse

            val imageview = mCalloutBallon.findViewById<View>(R.id.badge) as ImageView
            val title = mCalloutBallon.findViewById(R.id.title) as TextView
            val desc = mCalloutBallon.findViewById(R.id.desc) as TextView
            if (place.smallimage?.isNotEmpty() == true) {
                Glide.with(mCalloutBallon)
                    .asBitmap()
                    .load(place.smallimage)
                    .centerCrop()
                    .into(BitmapImageViewTarget(imageview))

            } else {
                imageview.setImageResource(R.drawable.no_image)
            }

            title.text = place.title
            desc.text = place.addr
        }
        else{
            val imageview = mCalloutBallon.findViewById<View>(R.id.badge) as ImageView
            val title = mCalloutBallon.findViewById(R.id.title) as TextView
            val desc = mCalloutBallon.findViewById(R.id.desc) as TextView

            imageview.setImageResource(R.drawable.aamulogoractangle)
            title.text = "현재 위치"
            desc.text = "현재 위치에서 여행을 시작하세요"
        }

        return mCalloutBallon
    }

    override fun getPressedCalloutBalloon(mapPOIItem: MapPOIItem?): View? {
        return null
    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            Log.e("src", src!!)
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned")
            myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Exception", e.message?:"")
            null
        }
    }

}