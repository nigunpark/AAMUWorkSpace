package com.aamu.aamuandroidapp.data.api.repositories

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import com.aamu.aamuandroidapp.data.api.AAMUApi
import com.aamu.aamuandroidapp.data.api.response.*
import com.aamu.aamuandroidapp.data.api.userLogin
import com.aamu.aamuandroidapp.util.contextL
import com.aamu.aamuandroidapp.util.getRealPathFromURI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.ln

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

    override suspend fun doLoginEmail(email: String,profile : String): String? {
        val map = HashMap<String,String>()
        map.put("email",email)
        val response = aamuApi.doLoginEmail(map)
        if(response.isSuccessful){
            val preferences: SharedPreferences =
                contextL.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            preferences.edit().putString("id", response.body()?.member?.username).commit()
            preferences.edit().putString("profile", profile).commit()
            return response.body()?.token
        }
        else{
            return null
        }
    }

    override suspend fun postToken(username: String, firebaseid: String) {
        val map : MutableMap<String,String> = HashMap<String,String>()
        map.put("id",username)
        map.put("firebaseid",firebaseid)
        val response = aamuApi.postToken(map)
        if(response.isSuccessful){
            Log.i("com.aamu.aamu","응 당연한 성공이야" + response.body())
        }
        else{
            Log.i("com.aamu.aamu","응 당연한 실페야" + response.body())
        }
    }

    override suspend fun delToken(username: String): Flow<Map<String,String>> = flow<Map<String, String>> {
        val response = aamuApi.delToken(username)
        if(response.isSuccessful){
            emit(response.body() ?: emptyMap())
        }
        else{
            emit(emptyMap())
        }
    }.catch {
        emit(emptyMap())
    }.flowOn(Dispatchers.IO)

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

    override suspend fun getPlaceOne(
        contentid: Int,
        contenttypeid: Int
    ): Flow<AAMUPlaceResponse> = flow<AAMUPlaceResponse> {
        val response = aamuApi.getPlaceOne(contentid,contenttypeid)
        if(response.isSuccessful){
            emit(response.body() ?: AAMUPlaceResponse())
        }
        else
            emit(AAMUPlaceResponse())
    }.catch {
        emit(AAMUPlaceResponse())
    }.flowOn(Dispatchers.IO)

    override suspend fun getKakaoReview(kakaoKey: String): Flow<AAMUKakaoReviewResponse> = flow<AAMUKakaoReviewResponse> {
        val response = aamuApi.getKakaoReview(kakaoKey)
        if (response.isSuccessful){
            emit(response.body() ?: AAMUKakaoReviewResponse())
        }
        else{
            emit(AAMUKakaoReviewResponse())
        }
    }.catch {
        emit(AAMUKakaoReviewResponse())
    }.flowOn(Dispatchers.IO)


    override suspend fun getRecentDiner(
        placey: Double,
        placex: Double,
        category : String
    ): Flow<List<AAMUPlaceResponse>> = flow {
        val response  = aamuApi.getRecentDiner(placey,placex,category)
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

    override suspend fun getPlannerBookMarkSelectList(id: String): Flow<List<AAMUBBSResponse>> = flow<List<AAMUBBSResponse>> {
        val response = aamuApi.getPlannerBookMarkSelectList(id)
        if(response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUBBSResponse>())
        }
        else{
            emit(emptyList<AAMUBBSResponse>())
        }
    }.catch {
        emit(emptyList<AAMUBBSResponse>())
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

    override suspend fun getMovingTime(
        firsty: Double,
        firstx: Double,
        secondy: Double,
        secondx: Double
    ): Flow<Map<String, String>> = flow {
        val response = aamuApi.getMovingTime(firsty, firstx, secondy, secondx)
        val failmap = HashMap<String,String>()
        failmap.put("result","fail")
        if(response.isSuccessful){
            emit(response.body() ?: failmap)
        }
        else
            emit(failmap)
    }.catch {
        val failmap = HashMap<String,String>()
        failmap.put("result","fail")
        emit(failmap)
    }.flowOn(Dispatchers.IO)

    override suspend fun getChatRoomList(id: String): Flow<List<AAMUChatRoomResponse>> = flow<List<AAMUChatRoomResponse>> {
        val response = aamuApi.getChatRoomList(id)
        if (response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUChatRoomResponse>())
        }
        else{
            emit(emptyList<AAMUChatRoomResponse>())
        }
    }.catch {
        emit(emptyList<AAMUChatRoomResponse>())
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

    override suspend fun getGramDetail(id: String, lno: Int): Flow<AAMUGarmResponse> = flow<AAMUGarmResponse> {
        val response = aamuApi.getGramDetail(id,lno)
        if(response.isSuccessful){
            emit(response.body() ?: AAMUGarmResponse())
        }
        else
            emit(AAMUGarmResponse())
    }.catch {
        emit(AAMUGarmResponse())
    }.flowOn(Dispatchers.IO)

    override suspend fun getGramLike(id: String, lno: Int): Flow<Map<String, String>> = flow<Map<String,String>> {
        val response = aamuApi.getGramLike(id, lno)
        val failmap = HashMap<String,String>()
        failmap.put("result","fail")
        if(response.isSuccessful){
            emit(response.body() ?: failmap)
        }
        else
            emit(failmap)
    }.catch {
        val failmap = HashMap<String,String>()
        failmap.put("result","fail")
        emit(failmap)
    }.flowOn(Dispatchers.IO)

    override suspend fun getGramBySearch(
        searchColumn: String,
        searchWord: String
    ): Flow<List<AAMUGarmResponse>> = flow<List<AAMUGarmResponse>> {
        val response = aamuApi.getGramBySearch(searchColumn,searchWord)
        if(response.isSuccessful){
            emit(response.body() ?: emptyList())
        }
        else{
            emit(emptyList())
        }
    }.catch {
        emit(emptyList())
    }.flowOn(Dispatchers.IO)

    override suspend fun getGramByPlaceList(contentid: Int): Flow<List<AAMUGarmResponse>> = flow<List<AAMUGarmResponse>> {
        val response = aamuApi.getGramByPlaceList(contentid)
        if (response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUGarmResponse>())
        }
        else
            emit(emptyList<AAMUGarmResponse>())
    }.catch {
        emit(emptyList<AAMUGarmResponse>())
    }.flowOn(Dispatchers.IO)

    override suspend fun postGram(
        multifiles: List<Uri>,
        map: Map<String, String>
    ): Flow<Map<String, Boolean>> = flow<Map<String,Boolean>> {
        var images = Vector<MultipartBody.Part>()
        for (index in 0..multifiles.size - 1) {
            val file = File(getRealPathFromURI(multifiles.get(index)))
            val surveyBody = file.asRequestBody("file".toMediaTypeOrNull())
            images.add(MultipartBody.Part.createFormData("multifiles", file.name, surveyBody))
        }

        val bodymap = HashMap<String, RequestBody>()
        for(key in map.keys){
            val act = map.get(key)?.toRequestBody("text/plain".toMediaTypeOrNull())
            if (act != null) {
                bodymap.put(key, act)
            }
        }

        Log.i("com.aamu.aamu","map : "+map.toString())
        val response = aamuApi.postGram(images.toList(), bodymap)
        Log.i("com.aamu.aamu","결과값이 나올때 까지 안찍힘")
        if (response.isSuccessful) {
            Log.i("com.aamu.aamu","몇초 걸렸음")
            emit(response.body() ?: emptyMap())
        } else {
            emit(emptyMap())
        }
    }.catch {
        emit(emptyMap())
    }.flowOn(Dispatchers.IO)

    override suspend fun postGramComment(gramComment: GramComment): Flow<Map<String, String>> = flow<Map<String,String>> {
        val response = aamuApi.postGramComment(gramComment)
        val failmap = HashMap<String,String>()
        failmap.put("result","insertNotSuccess")
        if (response.isSuccessful){
            if(response.body()?.getOrDefault("id",null) != null){
                emit(response.body() ?: failmap)
            }
            else{
                emit(failmap)
            }
        }
    }.catch {
        val failmap = HashMap<String,String>()
        failmap.put("result","insertNotSuccess")
        emit(failmap)
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

    override suspend fun getBBSOne(rbn: Int): Flow<AAMUBBSResponse> = flow<AAMUBBSResponse> {
        val response = aamuApi.getBBSOne(rbn)
        if (response.isSuccessful){
            emit(response.body() ?: AAMUBBSResponse())
        }
        else
            emit(AAMUBBSResponse())
    }.catch {
        emit(AAMUBBSResponse())
    }.flowOn(Dispatchers.IO)

    override suspend fun postReview(review: Review): Flow<Map<String, String>> = flow<Map<String, String>> {
        val response = aamuApi.postReview(review)
        val failmap = HashMap<String,String>()
        failmap.put("result","insertNotSuccess")
        if (response.isSuccessful){
            if(response.body()?.get("result").equals("insertNotSuccess")){
                emit(failmap)
            }
            else{
                emit(response.body() ?: failmap)
            }
        }
        else {
            emit(failmap)
        }
    }.catch {
        val failmap = HashMap<String,String>()
        failmap.put("result","insertNotSuccess")
        emit(failmap)
    }.flowOn(Dispatchers.IO)

    override suspend fun getNotiList(id: String): Flow<List<AAMUNotiResponse>> = flow<List<AAMUNotiResponse>> {
        val response = aamuApi.getNotiList(id)
        if(response.isSuccessful){
            emit(response.body() ?: emptyList<AAMUNotiResponse>())
        }
        else{
            emit(emptyList<AAMUNotiResponse>())
        }
    }.catch {
        emit(emptyList<AAMUNotiResponse>())
    }.flowOn(Dispatchers.IO)

    override suspend fun putNoti(nano : Int): Flow<Map<String, String>> = flow<Map<String, String>> {
        val map = HashMap<String,String>()
        map.put("nano",nano.toString())
        val response = aamuApi.putNoti(map)
        if(response.isSuccessful){
            emit(response.body() ?: emptyMap())
        }
        else{
            emit(emptyMap())
        }
    }.catch {

    }.flowOn(Dispatchers.IO)

    override suspend fun delNoti(nano: Int): Flow<Map<String, String>> = flow<Map<String, String>> {
        val response = aamuApi.delNoti(nano)
        if(response.isSuccessful){
            emit(response.body() ?: emptyMap())
        }
        else{
            emit(emptyMap())
        }
    }.catch {
        emit(emptyMap())
    }.flowOn(Dispatchers.IO)

    override suspend fun getUserInfo(id: String): Flow<AAMUUserInfo> = flow<AAMUUserInfo> {
        val response = aamuApi.getUserInfo(id)
        if(response.isSuccessful){
            emit(response.body() ?: AAMUUserInfo())
        }
        else{
            emit(AAMUUserInfo())
        }
    }.catch {
        emit(AAMUUserInfo())
    }.flowOn(Dispatchers.IO)
}
