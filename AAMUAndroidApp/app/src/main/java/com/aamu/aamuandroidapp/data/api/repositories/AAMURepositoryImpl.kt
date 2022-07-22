package com.aamu.aamuandroidapp.data.api.repositories

import android.util.Log
import com.aamu.aamuandroidapp.data.api.AAMUApi
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.AAMUUserResponse
import com.aamu.aamuandroidapp.data.api.userLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

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
        if(response.isSuccessful)
            return true
        return false
    }

    override suspend fun getRecentPlace(
        placey: Double,
        placex: Double
    ): Flow<List<AAMUPlaceResponse>> = flow {
        val response  = aamuApi.getRecentPlace(placey,placex)
        if(response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUPlaceResponse>())
        }
        else{
            emit(emptyList<AAMUPlaceResponse>())
        }
    }.catch {
        emit(emptyList<AAMUPlaceResponse>())
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecentDiner(
        placey: Double,
        placex: Double
    ): Flow<List<AAMUPlaceResponse>> = flow {
        val response  = aamuApi.getRecentDiner(placey,placex)
        if(response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUPlaceResponse>())
        }
        else{
            emit(emptyList<AAMUPlaceResponse>())
        }
    }.catch {
        emit(emptyList<AAMUPlaceResponse>())
    }.flowOn(Dispatchers.IO)

    override suspend fun getPlannerSelectOne(rbn: Int): Flow<AAMUPlannerSelectOne> = flow<AAMUPlannerSelectOne> {
        val response = aamuApi.getPlannerSelectOne(rbn)

        if (response.isSuccessful){
            emit(response.body() ?: AAMUPlannerSelectOne(null,null,null,null,null,null,null,null))
        }
        else{
            emit(AAMUPlannerSelectOne(null,null,null,null,null,null,null,null))
        }
    }.catch {
        emit(AAMUPlannerSelectOne(null,null,null,null,null,null,null,null))
    }.flowOn(Dispatchers.IO)
}
