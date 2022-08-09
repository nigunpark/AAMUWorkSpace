package com.aamu.aamuandroidapp.components.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import kotlinx.coroutines.launch

class ProfileScreenViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileScreenViewModel(context) as T
    }
}

class ProfileScreenViewModel (context: Context) : ViewModel(){
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    private val context = context

    fun getUserInfo(){
        val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val userid : String? = preferences.getString("id",null)
        viewModelScope.launch {
            aamuRepository.getUserInfo(userid?:"")
        }
    }
}