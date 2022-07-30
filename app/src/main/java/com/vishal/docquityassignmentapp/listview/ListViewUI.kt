package com.vishal.docquityassignmentapp.listview

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.vishal.docquityassignmentapp.R
import com.vishal.docquityassignmentapp.compose.ui.LightBlue
import com.vishal.docquityassignmentapp.compose.ui.Navy
import com.vishal.docquityassignmentapp.compose.ui.typography
import com.vishal.docquityassignmentapp.network.ResultState
import com.vishal.docquityassignmentapp.repository.ListItemEntity
import kotlinx.coroutines.launch

@Composable
fun ListViewUI(
    viewModel: ListViewViewmodel,
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    when (viewModel.listItemResponse.value.state) {
        ResultState.PROGRESS -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }
        ResultState.SUCCESS -> {
            viewModel.listItemResponse.value.result?.let {
                ListViewUI(
                    searchValue = viewModel.searchId.value,
                    onValueChange = viewModel.onSearchTextChanged,
                    onSearch = {
                        focusManager.clearFocus()
                        scope.launch { viewModel.onSearchClick() }
                    },
                    listItemResponse = it,
                )
            } ?: run {
                ErrorUI {
                    scope.launch { viewModel.getPosts(true) }
                }
            }
        }
        ResultState.FAILURE -> {
            ErrorUI {
                scope.launch { viewModel.getPosts(true) }
            }
        }
    }

    when (viewModel.singleListItemResponse.value.state) {
        ResultState.PROGRESS -> Unit
        ResultState.SUCCESS -> {
            viewModel.singleListItemResponse.value.result?.let {
                ListViewUI(
                    searchValue = viewModel.searchId.value,
                    onValueChange = viewModel.onSearchTextChanged,
                    onSearch = {
                        focusManager.clearFocus()
                        scope.launch { viewModel.onSearchClick() }
                    },
                    listItemResponse = listOf(it),
                )
            } ?: run {
                ErrorUI {
                    scope.launch {
                        viewModel.getPost(retry = true,
                            id = viewModel.searchId.value.toInt())
                    }
                }
            }
        }
        ResultState.FAILURE -> {
            ErrorUI {
                scope.launch {
                    viewModel.getPost(retry = true,
                        id = viewModel.searchId.value.toInt())
                }
            }
        }
    }
}

@Composable
private fun ListViewUI(
    modifier: Modifier = Modifier,
    searchValue: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    listItemResponse: List<ListItemEntity>,
) {

    Column(
        modifier = modifier.background(LightBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        SearchBarUI(searchValue = searchValue, onValueChange = onValueChange, onSearch = onSearch)
        Spacer(modifier = Modifier.size(6.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(
                count = listItemResponse.size,
                key = { index ->
                    listItemResponse[index].id
                },
            ) { index ->
                listItemResponse[index].let {
                    Spacer(modifier = Modifier.size(3.dp))
                    ListViewItem(listItemEntity = it, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.size(3.dp))
                }
            }
        }
    }


}

@Composable
private fun ErrorUI(
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .zIndex(10f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_api_error),
            contentDescription = null,
            modifier = Modifier.padding(32.dp)
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = "Something went wrong",
            style = typography.body1,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = onRetryClick) {
            Text(
                text = "Retry",
                style = typography.button,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun ListViewItem(
    modifier: Modifier = Modifier,
    listItemEntity: ListItemEntity,
) {

    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier
        .padding(6.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ))) {
        Column(
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                }
                .padding(6.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = listItemEntity.title,
                    color = Color.Black,
                    style = typography.button,
                )
                Spacer(modifier = Modifier.size(12.dp))
                if (expanded) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_up_arrow),
                        contentDescription = null,
                        tint = Navy,
                        modifier = Modifier.size(24.dp),
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_down_arrow),
                        contentDescription = null,
                        tint = Navy,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
            if (expanded) {

                Text(
                    text = listItemEntity.body,
                    color = Color.Black,
                    style = typography.caption,
                )
            }
        }
    }
}

@Composable
private fun SearchBarUI(
    modifier: Modifier = Modifier,
    searchValue: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = searchValue,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Search),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = White,
            focusedBorderColor = LightBlue,
            unfocusedBorderColor = Black,
            cursorColor = Black,
            textColor = Black,
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Navy,
                modifier = Modifier.size(24.dp),
            )
        },
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() },
        )
    )
}