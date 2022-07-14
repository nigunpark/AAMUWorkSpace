package com.aamu.aamuandroidapp.data.api.repositories

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
}
