package com.vishal.docquityassignmentapp.repository

import com.vishal.docquityassignmentapp.network.ApiResult
import com.vishal.docquityassignmentapp.network.apiCall
import com.vishal.docquityassignmentapp.network.listItemService

object ListItemRepository {

    suspend fun getPosts(): ApiResult<List<ListItemEntity>> {
        return apiCall { listItemService.getPosts() }
    }

    suspend fun getPost(id: Int): ApiResult<ListItemEntity> {
        return apiCall { listItemService.getPost(id) }
    }
}