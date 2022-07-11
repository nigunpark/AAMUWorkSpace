package com.aamu.aamuandroidapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

interface AAMUApi {

    companion object {
        private const val BASE_URL = ""

        operator fun invoke():AAMUApi{
            val requestInterceptor  = Interceptor{ chain ->

                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization","Bearer "+"")
                    .build()

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