package com.aamu.aamuandroidapp.components.routebbs

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModel
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.util.carousel.PagerState
import kotlinx.coroutines.launch

class RouteViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RouteViewModel(context) as T
    }
}

class RouteViewModel(context: Context) : ViewModel(){

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val aamuBBSList = MutableLiveData<List<AAMUBBSResponse>>()
    val errorLiveData = MutableLiveData<String>()

    val currentPage = MutableLiveData<Int>(0)

    val context = context

    fun getaamuBBSList(){
        viewModelScope.launch {
            aamuRepository.getBBSList()
                .collect{ bbsList ->
                    if(bbsList.isNotEmpty()){
                        aamuBBSList.value = bbsList
                    }
                    else{
                        errorLiveData.value = "리스트를 받아오는데 실페했습니다"
                        Toast.makeText(context,"리스트를 받아오는데 실페했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
    }
}