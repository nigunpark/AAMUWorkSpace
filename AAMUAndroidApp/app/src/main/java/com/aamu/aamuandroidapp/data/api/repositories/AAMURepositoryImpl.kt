package com.aamu.aamuandroidapp.data.api.repositories

import android.util.Log
import com.aamu.aamuandroidapp.data.api.AAMUApi
import com.aamu.aamuandroidapp.data.api.response.AAMUUserResponse
import com.aamu.aamuandroidapp.data.api.userLogin

class AAMURepositoryImpl(
    private val aamuApi : AAMUApi
) : AAMURepository {
    override suspend fun dologin(username : String, password : String): String? {
        val response = aamuApi.doLogin(userLogin(username,password))
        if(response.isSuccessful){
            return response.body()?.token
        }
        else{
            return null
        }
    }

    override suspend fun isok(): Boolean {
        val response = aamuApi.isok()
        Log.i("com.aamu.aamu","response.isSuccessful : "+response.code()+" response body : " + response.body())
        if(response.isSuccessful)
            return true
        return false
    }
}
