package com.aamu.aamuandroidapp.data.api

import android.widget.Toast
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.AAMUUserResponse
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

    @GET("isOK")
    suspend fun isok() : Response<String>

    @GET("info/recentplace")
    suspend fun getRecentPlace(@Query("placey") placey : Double,@Query("placex") placex : Double) : Response<List<AAMUPlaceResponse>>

    @GET("info/recentdiner")
    suspend fun getRecentDiner(@Query("placey") placey : Double,@Query("placex") placex : Double) : Response<List<AAMUPlaceResponse>>

    @GET("planner/selectonemap")
    suspend fun getPlannerSelectOne(@Query("rbn") rbn : Int) : Response<AAMUPlannerSelectOne>

    companion object {
        private const val BASE_URL = "http://192.168.0.19:8080/aamurest/"

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