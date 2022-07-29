package com.vishal.docquityassignmentapp.listview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vishal.docquityassignmentapp.compose.composeViewWithSurface
import com.vishal.docquityassignmentapp.getInjector

class ListViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val listViewViewmodel = ViewModelProvider(this,
            getInjector(requireContext()).provideMovieViewModelFactory()).get(ListViewViewmodel::class.java)


        return composeViewWithSurface {
            ListViewUI(viewModel = listViewViewmodel)
        }
    }
}