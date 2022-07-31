package com.aamu.aamuandroidapp.data.api.repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.aamu.aamuandroidapp.data.api.AAMUApi
import com.aamu.aamuandroidapp.data.api.response.*
import com.aamu.aamuandroidapp.data.api.userLogin
import com.aamu.aamuandroidapp.util.contextL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class AAMURepositoryImpl(
    private val aamuApi : AAMUApi
) : AAMURepository {
    override suspend fun dologin(username : String, password : String): String? {
        val response = aamuApi.doLogin(userLogin(username,password))
        if(response.isSuccessful){
            val preferences: SharedPreferences =
                contextL.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            preferences.edit().putString("id", response.body()?.member?.username).commit()
            preferences.edit().putString("profile", response.body()?.userprofile).commit()
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

    override suspend fun getPlannerSelectList(
    ): Flow<List<AAMUPlannerSelectOne>> = flow<List<AAMUPlannerSelectOne>> {
        val response = aamuApi.getPlannerSelectList()
        if(response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUPlannerSelectOne>())
        }
        else{
            emit(emptyList<AAMUPlannerSelectOne>())
        }
    }.catch {
        emit(emptyList<AAMUPlannerSelectOne>())
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

    override suspend fun getChatMessageList(map: Map<String, String>): Flow<List<AAMUChatingMessageResponse>> = flow<List<AAMUChatingMessageResponse>> {
        val response = aamuApi.getChatMessageList(map)
        if (response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUChatingMessageResponse>())
        }
        else
            emit(emptyList<AAMUChatingMessageResponse>())
    }.catch {
        emit(emptyList<AAMUChatingMessageResponse>())
    }.flowOn(Dispatchers.IO)

    override suspend fun getGramList(id: String): Flow<List<AAMUGarmResponse>> = flow<List<AAMUGarmResponse>> {
        val response = aamuApi.getGramList(id)
        if (response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUGarmResponse>())
        }
        else
            emit(emptyList<AAMUGarmResponse>())
    }.catch {
        emit(emptyList<AAMUGarmResponse>())
    }.flowOn(Dispatchers.IO)

    override suspend fun getBBSList(): Flow<List<AAMUBBSResponse>> = flow<List<AAMUBBSResponse>> {
        val response = aamuApi.getBBSList()
        if (response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUBBSResponse>())
        }
        else
            emit(emptyList<AAMUBBSResponse>())
    }.catch {
        emit(emptyList<AAMUBBSResponse>())
    }.flowOn(Dispatchers.IO)
}
