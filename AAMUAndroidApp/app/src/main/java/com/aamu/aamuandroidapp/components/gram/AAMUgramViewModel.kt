package com.aamu.aamuandroidapp.components.gram

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.pluck.data.PluckImage
import com.aamu.aamuandroidapp.util.contextL
import com.aamu.aamuandroidapp.util.getLatLng
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AAMUgramViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUgramViewModel(context) as T
    }
}

class  AAMUgramViewModel(context : Context) : ViewModel(){
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val aamuGramList = MutableLiveData<List<AAMUGarmResponse>>()
    val errorLiveData = MutableLiveData<String>()

    val recentPlaces = MutableLiveData<List<AAMUPlaceResponse>>()

    val uriArray = MutableLiveData<ArrayList<Uri>>()

    val context = context

    fun getGramList(){
        viewModelScope.launch {
            val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            val userid : String? = preferences.getString("id",null)
            aamuRepository.getGramList(userid.toString())
                .collect{ aamuGram ->
                    if(aamuGram.isNotEmpty()){
                        aamuGramList.value = aamuGram
                    }
                    else{
                        errorLiveData.value = "리스트를 받아오는데 실페했습니다"
                        Toast.makeText(context,"리스트를 받아오는데 실페했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
    }

    fun getGramLike(lno : Int){
        viewModelScope.launch {
            val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            val userid : String? = preferences.getString("id",null)
            aamuRepository.getGramLike(userid.toString(),lno)
                .collect{
                    if(it.get("isLike")?.isNotEmpty() == true){
                        getGramList()
                    }
            }
        }
    }

    fun getGramRcentPlaces() = viewModelScope.launch{
        val location : Location? = getLatLng()
        aamuRepository.getRecentPlace(location?.latitude ?:33.450701,location?.longitude ?:126.570667)
            .collect { aamuplaces ->
                if (aamuplaces.isNotEmpty()){
                    recentPlaces.value = aamuplaces
                }
                else{
                    errorLiveData.value = "주변 장소를 찾는데 실패하였습니다"
                    Toast.makeText(context,"주변 장소를 찾는데 실패하였습니다", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun seturiArry(list: List<PluckImage>) {
        val templist = ArrayList<Uri>()
        for(item in list){
            templist.add(item.uri)
        }
        uriArray.value = templist
    }

    fun postGram(
        map: Map<String,String>,
    ) {
        if(uriArray.value?.isNotEmpty()==true) {
            viewModelScope.launch {

                val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
                val userprofile : String? = preferences.getString("profile",null)
                val tempphoto = Vector<String>()
                for(tempuri in uriArray.value!!){
                    tempphoto.add(tempuri.toString())
                }

                val tempList = aamuGramList.value?.toMutableList()
                tempList?.add(0,AAMUGarmResponse(
                    photo = tempphoto,
                    ctitle = map.get("ctitle"),
                    contentid = map.get("contentid"),
                    tname = (map.get("tname")?.split(",")),
                    id=map.get("id"),
                    content = map.get("content"), userprofile = userprofile))
                aamuGramList.value = tempList?.toList()


                aamuRepository.postGram(uriArray.value!!.toList(),map)
                    .collect{
                        if(it.isNotEmpty()){
                            getGramList()
                        }
                        else{
                            Toast.makeText(contextL,"포스팅에 실패했어요",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}