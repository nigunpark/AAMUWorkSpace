package com.aamu.aamuandroidapp.data.api.repositories

import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import kotlinx.coroutines.flow.Flow

interface AAMURepository {
    suspend fun dologin(username : String, password : String) : String?
    suspend fun isok() : Boolean
    suspend fun getRecentPlace(placey : Double,placex : Double) : Flow<List<AAMUPlaceResponse>>
    suspend fun getRecentDiner(placey : Double,placex : Double) : Flow<List<AAMUPlaceResponse>>
    suspend fun getPlannerSelectList() : Flow<List<AAMUPlannerSelectOne>>
    suspend fun getPlannerSelectOne(rbn : Int) : Flow<AAMUPlannerSelectOne>
}