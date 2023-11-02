package com.example.snphmsapo.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCall {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://mobile-test.mysapogo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request()
                        val newRequest = request.newBuilder()
                            .addHeader("X-Sapo-SessionId", "0a7d5c7ea94f78da201c379786eaa091")
                            .addHeader("X-Sapo-Client","Android")
                            .addHeader("X-Sapo-LocationId","286483")
                            .addHeader("Content-Type","application/json")
                            .build()
                        chain.proceed(newRequest)

                    }.build()
            )
            .build()
    }
    val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

