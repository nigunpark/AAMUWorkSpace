package com.aamu.aamuandroidapp.data.api.repositories

interface AAMURepository {
    suspend fun dologin(username : String, password : String) : String?
}