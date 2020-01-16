package com.alp.myapplication.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api {
    private lateinit var dataApiInstance: Retrofit
    private fun getDataApi(): Retrofit {
        if (!Api::dataApiInstance.isInitialized) {
            val client = okHttpBuilder().build()
            dataApiInstance = retrofitBuilder(client).baseUrl("https://my-json-server.typicode.com").build()
        }
        return dataApiInstance
    }

    val dataClient = getDataApi().create(IDataService::class.java)!!

    private fun okHttpBuilder() = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        //.addInterceptor(HeadersInterceptor())

    private fun retrofitBuilder(client: OkHttpClient) = Retrofit.Builder().client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
}

const val API = "/oguzayan/kuka/"