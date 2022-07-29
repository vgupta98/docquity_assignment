package com.vishal.docquityassignmentapp.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.vishal.docquityassignmentapp.compose.ui.DocquityAppTheme
import com.vishal.docquityassignmentapp.network.ApiResult

@Composable
fun ImageFromUrl(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String? = null,
) {
    val shimmerState = remember { mutableStateOf(false) }
    val mModifier = modifier
        .shimmerPlaceholder(shimmerState.value)


    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        onState = {
            when(it) {
                is AsyncImagePainter.State.Loading -> {
                    shimmerState.value = true
                }
                is AsyncImagePainter.State.Success -> {
                    shimmerState.value = false
                }
                is AsyncImagePainter.State.Error -> {
                    shimmerState.value = false
                }
                else -> {
                    shimmerState.value = false
                }
            }
        },
        modifier = mModifier
    )
}

fun Modifier.shimmerPlaceholder(
    visible: Boolean = true,
    shape: Shape = RectangleShape,
): Modifier = placeholder(
    visible = visible,
    color = Color(0xFF666666),
    shape = shape,
    highlight = PlaceholderHighlight.shimmer(
        highlightColor = Color(0xFF888888),
        animationSpec = PlaceholderDefaults.shimmerAnimationSpec,
    ),
)

typealias StateAsyncResult<T> = MutableState<ApiResult<T>>

fun <T> stateApiResult(): StateAsyncResult<T> {
    return mutableStateOf(ApiResult())
}

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
