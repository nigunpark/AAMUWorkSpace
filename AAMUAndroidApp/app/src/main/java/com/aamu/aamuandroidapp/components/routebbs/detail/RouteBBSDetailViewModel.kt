package com.aamu.aamuandroidapp.components.routebbs.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModel
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import kotlinx.coroutines.launch

class RouteBBSDetailViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RouteBBSDetailViewModel(context) as T
    }
}

class RouteBBSDetailViewModel(context: Context) : ViewModel(){

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val errorLiveData = MutableLiveData<String>()

    init {
        viewModelScope.launch {

        }
    }
}