package com.aamu.aamuandroidapp.data.api.repositories

import com.aamu.aamuandroidapp.data.api.AAMUApi
import com.aamu.aamuandroidapp.data.api.response.AAMUUserResponse

class AAMURepositoryImpl(
    private val aamuApi : AAMUApi
) : AAMURepository {
    override suspend fun dologin(username : String, password : String): String? {
        val response = aamuApi.doLogin(username,password)
        if(response.isSuccessful){
            return response.body()?.token
        }
        else{
            throw NullPointerException()
        }
    }
}