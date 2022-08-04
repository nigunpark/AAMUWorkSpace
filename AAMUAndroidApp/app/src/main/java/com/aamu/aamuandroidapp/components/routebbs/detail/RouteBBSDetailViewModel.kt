package com.aamu.aamuandroidapp.components.routebbs.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModel
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import kotlinx.coroutines.launch

class RouteBBSDetailViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RouteBBSDetailViewModel(context) as T
    }
}

class RouteBBSDetailViewModel(context: Context) : ViewModel(){

    private val context = context

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val bbsDetail = MutableLiveData<AAMUBBSResponse>()
    val errorLiveData = MutableLiveData<String>()

    fun getBBSListOne(rbn : Int){
        viewModelScope.launch {
            aamuRepository.getBBSOne(rbn)
                .collect{ bbsitem ->
                    if (bbsitem.rbn != null){
                        bbsDetail.value = bbsitem
                    }
                    else{
                        errorLiveData.value = "자세히 보기를 받아오는데 실페했습니다"
                        Toast.makeText(context,"자세히 보기를 받아오는데 실페했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
    }
}