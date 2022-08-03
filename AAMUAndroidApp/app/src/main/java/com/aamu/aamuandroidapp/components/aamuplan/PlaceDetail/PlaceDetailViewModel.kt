package com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import net.daum.mf.map.api.MapView

class PlaceDetailViewModelFactory(val context: Context,val place : AAMUPlaceResponse) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaceDetailViewModel(context,place) as T
    }
}

class PlaceDetailViewModel(context: Context,place : AAMUPlaceResponse) : ViewModel(){

    val mapView = MapView(context)
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val placeLiveData = MutableLiveData<AAMUPlaceResponse>()
    val errorLiveData = MutableLiveData<String>()

    init {
        mapView.removeAllPOIItems()
        mapView.removeAllPolylines()
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
                
            }
        }
        else{
            errorLiveData.value = "장소 받아오는데 실페했습니다"
            Toast.makeText(context,"장소 받아오는데 실페했습니다", Toast.LENGTH_LONG)
        }
    }
}