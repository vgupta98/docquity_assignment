package com.vishal.docquityassignmentapp.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitGson: Gson by lazy {
    GsonBuilder()
        .disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}

private val gsonConverterFactory by lazy {
    GsonConverterFactory.create(retrofitGson)
}

private val API_BASE_URL = "https://jsonplaceholder.typicode.com"

private val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
}

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(API_BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

val listItemService by lazy { retrofit.create(ListItemService::class.java) }
