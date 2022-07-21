package com.aamu.aamuandroidapp.data.api

import android.app.Activity
import android.util.Log
import com.aamu.aamuandroidapp.data.api.response.AAMUPlaceResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUUserResponse
import com.aamu.aamuandroidapp.util.getToken
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface AAMUApi {

    @POST("authenticate")
    suspend fun doLogin(@Body user: userLogin): Response<AAMUUserResponse>

    @GET("isOK")
    suspend fun isok() : Response<String>

    @GET("info/recentplace")
    suspend fun getRecentPlace(@Query("placey") placey : Double,@Query("placex") placex : Double) : Response<List<AAMUPlaceResponse>>

    @GET("info/recentdiner")
    suspend fun getRecentDiner(@Query("placey") placey : Double,@Query("placex") placex : Double) : Response<List<AAMUPlaceResponse>>

    companion object {
        private const val BASE_URL = "http://192.168.45.107:8080/aamurest/"

        operator fun invoke():AAMUApi{
            val requestInterceptor  = Interceptor{ chain ->

                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization","Bearer "+ (getToken()?:""))
                    .build()

                return@Interceptor chain.proceed(request)
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
    val username: String,
    val password: String
)