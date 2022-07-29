package com.vishal.docquityassignmentapp.network

import com.vishal.docquityassignmentapp.repository.ListItemEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ListItemService {

    @GET("/posts")
    suspend fun getPosts(): Response<List<ListItemEntity>>

    @GET("/posts/{id}")
    suspend fun getPost(@Path(value = "id") id: Int): Response<ListItemEntity>
}