package com.aamu.aamuandroidapp.data.api

import android.widget.Toast
import com.aamu.aamuandroidapp.data.api.response.*
import com.aamu.aamuandroidapp.util.contextL
import com.aamu.aamuandroidapp.util.getToken
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.lang.Exception
import java.net.URL

interface AAMUApi {

    @POST("authenticate")
    suspend fun doLogin(@Body user: userLogin): Response<AAMUUserResponse>

    @POST("authenticate/email")
    suspend fun doLoginEmail(@Body map : Map<String,String>): Response<AAMUUserResponse>

    @POST("notification/firebase")
    suspend fun postToken(@Body map: Map<String,String>): Response<Map<String,String>>

    @DELETE("notification/firebase")
    suspend fun delToken(@Query("id") id: String): Response<Map<String,String>>

    @GET("isOK")
    suspend fun isok() : Response<String>

    @GET("info/recentplace")
    suspend fun getRecentPlace(@Query("placey") placey : Double,@Query("placex") placex : Double) : Response<List<AAMUPlaceResponse>>

    @GET("info/oneplace")
    suspend fun getPlaceOne(@Query("contentid") contentid : Int,@Query("contenttypeid") contenttypeid : Int) : Response<AAMUPlaceResponse>

    @GET("info/review")
    suspend fun getKakaoReview(@Query("kakaoKey") kakaoKey : String ) : Response<AAMUKakaoReviewResponse>

    @GET("info/recentdiner")
    suspend fun getRecentDiner(@Query("placey") placey : Double,@Query("placex") placex : Double,@Query("category") category : String) : Response<List<AAMUPlaceResponse>>

    @GET("planner/selectList")
    suspend fun getPlannerSelectList() : Response<List<AAMUPlannerSelectOne>>

    @GET("bbs/bookmark/list")
    suspend fun getPlannerBookMarkSelectList(@Query("id") id : String) : Response<List<AAMUBBSResponse>>

    @GET("planner/selectonemap")
    suspend fun getPlannerSelectOne(@Query("rbn") rbn : Int) : Response<AAMUPlannerSelectOne>

    @GET("data/mvtm")
    suspend fun getMovingTime(@Query("firsty") firsty : Double,@Query("firstx ") firstx : Double,@Query("secondy") secondy  : Double,@Query("secondx") secondx : Double) : Response<Map<String,String>>

    @GET("chat/rooms")
    suspend fun getChatRoomList(@Query("id") id : String): Response<List<AAMUChatRoomResponse>>

    @GET("chat/room")
    suspend fun getChatMessageList(@QueryMap map : Map<String,String>): Response<List<AAMUChatingMessageResponse>>

    @GET("gram/selectList")
    suspend fun getGramList(@Query("id") id : String) : Response<List<AAMUGarmResponse>>

    @GET("gram/SelectOne")
    suspend fun getGramDetail(@Query("id") id : String,@Query("lno") lno: Int) : Response<AAMUGarmResponse>

    @GET("gram/like")
    suspend fun getGramLike(@Query("id") id : String,@Query("lno") lno : Int) : Response<Map<String,String>>

    @GET("gram/selectList")
    suspend fun getGramBySearch(@Query("searchColumn") searchColumn : String,@Query("searchWord") searchWord : String ) : Response<List<AAMUGarmResponse>>

    @GET("gram/selectList")
    suspend fun getGramByPlaceList(@Query("contentid") contentid : Int) : Response<List<AAMUGarmResponse>>

    @POST("gram/comment/edit")
    suspend fun postGramComment(@Body gramComment : GramComment ) : Response<Map<String,String>>

    @GET("bbs/SelectList")
    suspend fun getBBSList() : Response<List<AAMUBBSResponse>>

    @GET("bbs/SelectOne/{rbn}")
    suspend fun getBBSOne(@Path("rbn") rbn: Int) : Response<AAMUBBSResponse>

    @POST("review/edit")
    suspend fun postReview(@Body review : Review) : Response<Map<String,String>>

    @GET("notification/list")
    suspend fun getNotiList(@Query("id") id : String) : Response<List<AAMUNotiResponse>>

    @GET("users/selectone")
    suspend fun getUserInfo(@Query("id") id : String) : Response<AAMUUserInfo>

    companion object {
        private const val BASE_URL = "http://192.168.45.107:8080/aamurest/"

        operator fun invoke():AAMUApi{
            val requestInterceptor  = Interceptor{ chain ->

                        val request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer " + (getToken() ?: ""))
                            .build()
                    try {
                        return@Interceptor chain.proceed(request)
                    }
                    catch (e : Exception){
                        val response : okhttp3.Response = okhttp3.Response.Builder()
                            .code(1)
                            .request(request)
                            .protocol(Protocol.HTTP_2)
                            .message("서버나 인터넷이 연결인 안됬어요!!")
                            .build()
                        return@Interceptor response
                    }
                }



            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                .build()
                .create(AAMUApi::class.java)
        }
    }
}

data class userLogin(
    var username: String,
    var password: String
)