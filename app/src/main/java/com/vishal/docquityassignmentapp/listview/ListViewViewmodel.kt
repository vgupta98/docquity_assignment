package com.vishal.docquityassignmentapp.listview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vishal.docquityassignmentapp.compose.stateApiResult
import com.vishal.docquityassignmentapp.network.ApiResult
import com.vishal.docquityassignmentapp.repository.ListItemEntity
import com.vishal.docquityassignmentapp.repository.ListItemRepository

class ListViewViewmodel private constructor(
    private val listItemRepository: ListItemRepository,
): ViewModel() {
    val listItemResponse = stateApiResult<List<ListItemEntity>>()
    private val emptyListItemResponse = ApiResult<List<ListItemEntity>>()

    suspend fun getPosts(retry: Boolean = false) {
        if (retry) {
            listItemResponse.value = emptyListItemResponse
        }
        listItemResponse.value = listItemRepository.getPosts()
    }

    class Factory(
        private val listItemRepository: ListItemRepository,
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListViewViewmodel(listItemRepository) as T
        }
    }
}