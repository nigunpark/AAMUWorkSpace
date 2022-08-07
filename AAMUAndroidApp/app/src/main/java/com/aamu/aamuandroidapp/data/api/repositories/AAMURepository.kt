package com.aamu.aamuandroidapp.data.api.repositories

import com.aamu.aamuandroidapp.data.api.response.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface AAMURepository {
    suspend fun dologin(username : String, password : String) : String?
    suspend fun postToken(username: String,firebaseid : String)
    suspend fun isok() : Boolean
    suspend fun getRecentPlace(placey : Double,placex : Double) : Flow<List<AAMUPlaceResponse>>
    suspend fun getPlaceOne(contentid : Int, contenttypeid : Int) : Flow<AAMUPlaceResponse>
    suspend fun getRecentDiner(placey : Double,placex : Double) : Flow<List<AAMUPlaceResponse>>
    suspend fun getPlannerSelectList() : Flow<List<AAMUPlannerSelectOne>>
    suspend fun getPlannerSelectOne(rbn : Int) : Flow<AAMUPlannerSelectOne>

    suspend fun getChatMessageList(map : Map<String,String>) : Flow<List<AAMUChatingMessageResponse>>

    suspend fun getGramList(id : String) : Flow<List<AAMUGarmResponse>>
    suspend fun getGramDetail(id : String,lno : Int) : Flow<AAMUGarmResponse>
    suspend fun getGramLike(id : String,lno : Int) : Flow<Map<String,String>>
    suspend fun getGramByPlaceList(contentid : Int) : Flow<List<AAMUGarmResponse>>

    suspend fun getBBSList() : Flow<List<AAMUBBSResponse>>
    suspend fun getBBSOne( rbn: Int) : Flow<AAMUBBSResponse>
    suspend fun postReview(review: Review) : Flow<Map<String,String>>

    suspend fun getNotiList(id : String) : Flow<List<AAMUNotiResponse>>
}