package com.aamu.aamuandroidapp.components.login

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.util.getToken
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel() as T
    }
}

class LoginViewModel() : ViewModel(){


    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val token = MutableLiveData<String>(getToken())
    var loggedIn = MutableLiveData(false)
    fun doLogin(username : String, password : String) = viewModelScope.launch{
        val retoken = aamuRepository.dologin(username = username, password = password)
        retoken?.let {
            token.value = it
        }
    }

//    fun setLoggedIn(boolean: Boolean){
//        loggedIn.value = boolean
//    }
}