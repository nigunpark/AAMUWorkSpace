package com.aamu.aamuandroidapp.data.api.repositories

import android.net.Uri
import com.aamu.aamuandroidapp.data.api.response.*
import kotlinx.coroutines.flow.Flow

interface AAMURepository {
    suspend fun dologin(username : String, password : String) : String?
    suspend fun doLoginEmail(email : String,profile : String) : String?
    suspend fun postToken(username: String,firebaseid : String)
    suspend fun delToken(username: String) : Flow<Map<String,String>>
    suspend fun isok() : Boolean
    suspend fun getRecentPlace(placey : Double,placex : Double) : Flow<List<AAMUPlaceResponse>>
    suspend fun getPlaceOne(contentid : Int, contenttypeid : Int) : Flow<AAMUPlaceResponse>
    suspend fun getKakaoReview(kakaoKey : String ) : Flow<AAMUKakaoReviewResponse>
    suspend fun getRecentDiner(placey : Double,placex : Double,category : String) : Flow<List<AAMUPlaceResponse>>

    suspend fun getPlannerSelectList() : Flow<List<AAMUPlannerSelectOne>>
    suspend fun getPlannerBookMarkSelectList(id : String) : Flow<List<AAMUBBSResponse>>

    suspend fun getPlannerSelectOne(rbn : Int) : Flow<AAMUPlannerSelectOne>

    suspend fun getMovingTime(firsty : Double,firstx : Double,secondy : Double,secondx : Double) : Flow<Map<String,String>>

    suspend fun getChatRoomList(id : String) : Flow<List<AAMUChatRoomResponse>>
    suspend fun getChatMessageList(map : Map<String,String>) : Flow<List<AAMUChatingMessageResponse>>

    suspend fun getGramList(id : String) : Flow<List<AAMUGarmResponse>>
    suspend fun getGramDetail(id : String,lno : Int) : Flow<AAMUGarmResponse>
    suspend fun getGramLike(id : String,lno : Int) : Flow<Map<String,String>>
    suspend fun getGramBySearch(searchColumn : String,searchWord : String ) : Flow<List<AAMUGarmResponse>>
    suspend fun getGramByPlaceList(contentid : Int) : Flow<List<AAMUGarmResponse>>
    suspend fun postGram(multifiles : ArrayList<Uri>,map : Map<String,String>,tname : List<String>) : Flow<Map<String,Boolean>>
    suspend fun postGramComment(gramComment: GramComment) : Flow<Map<String,String>>

    suspend fun getBBSList() : Flow<List<AAMUBBSResponse>>
    suspend fun getBBSOne( rbn: Int) : Flow<AAMUBBSResponse>
    suspend fun postReview(review: Review) : Flow<Map<String,String>>

    suspend fun getNotiList(id : String) : Flow<List<AAMUNotiResponse>>
    suspend fun putNoti(nano : Int) : Flow<Map<String,String>>
    suspend fun delNoti(nano : Int) : Flow<Map<String,String>>

    suspend fun getUserInfo(id : String) : Flow<AAMUUserInfo>
}