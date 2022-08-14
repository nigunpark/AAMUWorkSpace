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
class LoginViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context) as T
    }
}

class LoginViewModel(context: Context) : ViewModel(){

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val token = MutableLiveData<String?>(getToken())
    val islogin = MutableLiveData(false)

    val context = context

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

    fun doLoginEmail(email: String,profile : String) = viewModelScope.launch {
        val retoken = aamuRepository.doLoginEmail(email,profile)
        retoken?.let {
            token.value = it
            FirebaseMessaging.getInstance().token.addOnSuccessListener {
                viewModelScope.launch{
                    val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
                    val id : String? = preferences.getString("id",null)
                    aamuRepository.postToken(id!!,it)
                }
            }
        }
    }

    fun isok() = viewModelScope.launch {
        if(aamuRepository.isok()){
            islogin.value = true
            FirebaseMessaging.getInstance().token.addOnSuccessListener {
                viewModelScope.launch{
                    val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
                    val id : String? = preferences.getString("id",null)
                    aamuRepository.postToken(id!!,it)
                }
            }
        }
        else{
            token.value = null
            delToken()
        }
    }

    fun delToken(){
        viewModelScope.launch{
            val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            val userid : String? = preferences.getString("id",null)
            if (userid != null) {
                aamuRepository.delToken(userid)
                    .collect{
                        if(it.isNotEmpty()){
                            context.getSharedPreferences("usersInfo",0).edit().clear().commit();
                        }
                    }
            }
        }
    }
}