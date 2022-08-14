package com.aamu.aamuandroidapp.components.gram.posting

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.pluck.data.PluckImage
import com.aamu.aamuandroidapp.util.getLatLng
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GramPostingViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GramPostingViewModel(context) as T
    }
}

class GramPostingViewModel(context: Context) : ViewModel() {

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()
    private val context = context


    val recentPlaces = MutableLiveData<List<AAMUPlaceResponse>>()
    val errorLiveData = MutableLiveData<String>()

    val uriArray = MutableLiveData<ArrayList<Uri>>()

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
        title: String,
        content: String,
        contentid: Int,
        tname: List<String>,
        navPopBackStack: () -> Unit
    ) {
        if(uriArray.value?.isNotEmpty()==true) {

            val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            val userid : String? = preferences.getString("id",null)

            val map = HashMap<String,String>()

            map.put("id",userid ?: "")
            map.put("ctitle",title)
            map.put("content",content)
            map.put("contentid",contentid.toString())

            viewModelScope.launch {
                aamuRepository.postGram(uriArray.value!!,map,tname)
                    .collect{
                        if(it.isNotEmpty()){
                            navPopBackStack.invoke()
                        }
                        else{
                            Toast.makeText(context,"포스팅에 실패했어요",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}