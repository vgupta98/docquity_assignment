package com.vishal.docquityassignmentapp.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.vishal.docquityassignmentapp.compose.ui.DocquityAppTheme

fun Fragment.composeViewWithTheme(
    content: @Composable () -> Unit,
): ComposeView {
    return ComposeView(requireContext()).apply {
        setContent {
            DocquityAppTheme(content = content)
        }
    }
}

fun Fragment.composeViewWithSurface(
    content: @Composable () -> Unit
): ComposeView {
    return composeViewWithTheme {
        Surface(color = MaterialTheme.colors.surface, content = content)
    }
}
