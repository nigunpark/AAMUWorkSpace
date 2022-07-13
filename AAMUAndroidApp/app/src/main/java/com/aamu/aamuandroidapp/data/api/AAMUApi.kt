package com.aamu.aamuandroidapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AAMUApi {

    @POST
    suspend fun login(@Body username : String, @Body password : String)

    companion object {
        private const val BASE_URL = "http://192.168.0.25:8080/aamurest/"

        operator fun invoke():AAMUApi{
            val requestInterceptor  = Interceptor{ chain ->

                val request = chain.request()
                    .newBuilder()
                    .build()

                if(1==1){
                    request.newBuilder().addHeader("Authorization","Bearer "+"").build()
                }

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return  Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(AAMUApi::class.java)
        }
    }
}