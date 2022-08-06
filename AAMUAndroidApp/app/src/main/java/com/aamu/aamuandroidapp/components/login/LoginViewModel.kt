package com.aamu.aamuandroidapp.components.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.util.contextL
import com.aamu.aamuandroidapp.util.getToken
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel() as T
    }
}

class LoginViewModel() : ViewModel(){

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val token = MutableLiveData<String?>(getToken())
    val islogin = MutableLiveData(false)
    fun doLogin(username : String, password : String) = viewModelScope.launch{
        val retoken = aamuRepository.dologin(username = username, password = password)
        retoken?.let {
            token.value = it
            FirebaseMessaging.getInstance().token.addOnSuccessListener {
                viewModelScope.launch{
                    aamuRepository.postToken(username,it)
                }
            }
        }
    }

    fun isok() = viewModelScope.launch {
        Log.i("com.aamu.aamu",token.value.toString())
        if(aamuRepository.isok()){
            islogin.value = true
        }
        else{
            token.value = null
        }

    }
}