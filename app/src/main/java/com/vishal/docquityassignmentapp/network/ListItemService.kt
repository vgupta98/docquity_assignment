package com.vishal.docquityassignmentapp.network

import com.vishal.docquityassignmentapp.repository.ListItemEntity
import retrofit2.Response
import retrofit2.http.GET

interface ListItemService {

    @GET("/posts")
    suspend fun getPosts(): Response<List<ListItemEntity>>
}