package com.vishal.docquityassignmentapp.listview

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vishal.docquityassignmentapp.compose.stateApiResult
import com.vishal.docquityassignmentapp.network.ApiResult
import com.vishal.docquityassignmentapp.repository.ListItemEntity
import com.vishal.docquityassignmentapp.repository.ListItemRepository
import kotlinx.coroutines.launch

class ListViewViewmodel private constructor(
    private val listItemRepository: ListItemRepository,
) : ViewModel() {
    val listItemResponse = stateApiResult<List<ListItemEntity>>()
    private val emptyListItemResponse = ApiResult<List<ListItemEntity>>()

    val singleListItemResponse by lazy { stateApiResult<ListItemEntity>() }
    private val emptySingleListItemResponse by lazy { ApiResult<ListItemEntity>() }

    val searchId by lazy { mutableStateOf("") }

    init {
        viewModelScope.launch {
            getPosts()
        }
    }

    suspend fun getPosts(retry: Boolean = false) {
        if (retry) {
            listItemResponse.value = emptyListItemResponse
        }
        listItemResponse.value = listItemRepository.getPosts()
    }

    suspend fun getPost(id: Int, retry: Boolean = false) {
        if (retry) {
            singleListItemResponse.value = emptySingleListItemResponse
        }
        singleListItemResponse.value = listItemRepository.getPost(id)
    }

    val onSearchTextChanged = fun(value: String) {
        if (value.toIntOrNull() != null || value.isEmpty()) {
            searchId.value = value
        }
    }

    suspend fun onSearchClick() {
        if (searchId.value.toIntOrNull() != null)
            getPost(searchId.value.toInt())
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