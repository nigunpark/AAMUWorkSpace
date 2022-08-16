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
import com.aamu.aamuandroidapp.util.contextL
import com.aamu.aamuandroidapp.util.getLatLng
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GramPostingViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GramPostingViewModel(context) as T
    }
}

class GramPostingViewModel(context: Context) : ViewModel() {

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

}