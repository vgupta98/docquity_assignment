package com.vishal.docquityassignmentapp

import android.content.Context
import com.vishal.docquityassignmentapp.listview.ListViewViewmodel
import com.vishal.docquityassignmentapp.repository.ListItemRepository

private lateinit var InjectorUtils: Injector

fun getInjector(context: Context): Injector {
    if (!::InjectorUtils.isInitialized) {
        InjectorUtils = Injector(context)
    }

    return InjectorUtils
}

class Injector(context: Context) {
    private val listItemRepository by lazy { ListItemRepository }

    fun provideMovieViewModelFactory(): ListViewViewmodel.Factory {
        return ListViewViewmodel.Factory(listItemRepository)
    }
}