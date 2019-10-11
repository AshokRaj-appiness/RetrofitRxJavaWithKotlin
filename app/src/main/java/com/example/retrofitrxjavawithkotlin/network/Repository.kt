package com.example.retrofitrxjavawithkotlin.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class Repository(baseUrl:String, isDebugEnabled:Boolean) {

    val retrofit:Retrofit

    init{
        val httpLogingInterceptor = HttpLoggingInterceptor()
        if(isDebugEnabled){
            httpLogingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }else{
            httpLogingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        val client = OkHttpClient.Builder().addInterceptor(httpLogingInterceptor).build()
        retrofit = Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }
}