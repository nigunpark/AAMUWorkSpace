package com.aamu.aamuandroidapp.data.api

import android.content.Context
import android.content.SharedPreferences
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepositoryImpl

object AAMUDIGraph {

    private val aamuApi = AAMUApi.invoke()

    fun createAAMURepository() : AAMURepository{
        return AAMURepositoryImpl(aamuApi = aamuApi)
    }

}