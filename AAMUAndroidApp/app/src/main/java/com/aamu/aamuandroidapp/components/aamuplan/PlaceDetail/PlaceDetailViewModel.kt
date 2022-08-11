package com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUKakaoReviewResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class PlaceDetailViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaceDetailViewModel(context) as T
    }
}

class PlaceDetailViewModel(context: Context) : ViewModel() , MapView.MapViewEventListener{

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()
    private val context = context
    val placeLiveData = MutableLiveData<AAMUPlaceResponse>()
    val kakoLiveData = MutableLiveData<AAMUKakaoReviewResponse>()
    val errorLiveData = MutableLiveData<String>()
    val errorKakaoLiveData = MutableLiveData<String>()

    fun getPlaceOne(place : AAMUPlaceResponse){
        if(place.contentid != null && place.contenttypeid != null) {
            viewModelScope.launch {
                aamuRepository.getPlaceOne(place.contentid!!, place.contenttypeid!!)
                    .collect {placeOne ->
                        if(placeOne.title != null){
                            placeLiveData.value = placeOne
                        }
                        else{
                            errorLiveData.value = "장소 받아오는데 실페했습니다"
                            Toast.makeText(context,"장소 받아오는데 실페했습니다", Toast.LENGTH_LONG)
                        }
                    }
            }
            viewModelScope.launch {
                //카카오리뷰 받아오기
                Log.i("com.aamu.aamu" , " place.kakaokey = ${place.kakaokey}")
                aamuRepository.getKakaoReview(place.kakaokey ?: "0")
                    .collect { kakaoreview ->
                        if (kakaoreview.basicInfo != null && kakaoreview.commentInfo != null ){
                            kakoLiveData.value = kakaoreview
                        }
                        else{
                            errorKakaoLiveData.value = "카카오 리뷰가 없습니다"
                        }

                    }
            }
        }
        else{
            errorLiveData.value = "장소 받아오는데 실페했습니다"
            Toast.makeText(context,"장소 받아오는데 실페했습니다", Toast.LENGTH_LONG)
        }
    }

    override fun onMapViewInitialized(mapView: MapView?) {}

    override fun onMapViewCenterPointMoved(mapView: MapView?, mapCenterPoint: MapPoint?) {}

    override fun onMapViewZoomLevelChanged(mapView: MapView?, zoomLevel: Int) {}

    override fun onMapViewSingleTapped(mapView: MapView?, mapPoint: MapPoint?) {}

    override fun onMapViewDoubleTapped(mapView: MapView?, mapPoint: MapPoint?) {}

    override fun onMapViewLongPressed(mapView: MapView?, mapPoint: MapPoint?) {}

    override fun onMapViewDragStarted(mapView: MapView?, mapPoint: MapPoint?) { }

    override fun onMapViewDragEnded(mapView: MapView?, mapPoint: MapPoint?) {}

    override fun onMapViewMoveFinished(mapView: MapView?, mapPoint: MapPoint?) {}
}