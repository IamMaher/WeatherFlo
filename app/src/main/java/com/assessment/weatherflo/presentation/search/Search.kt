package com.assessment.weatherflo.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.assessment.weatherflo.R
import com.assessment.weatherflo.core.extenstion.clearFocusOnKeyboardDismiss
import com.assessment.weatherflo.domain.entity.cities.CityEntity
import com.assessment.weatherflo.presentation.components.InfinityText
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(
    screen: Int,
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit,
    onSelectResult: (Int, CityEntity) -> Unit,
    onSearchCurrentLocation: (Int) -> Unit
) {
    val state = viewModel.state
    val searchText by viewModel.textSearch.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(true) {
        onDispose { keyboardController?.hide() }
    }

    SearchContent(state = state,
        text = searchText,
        onTextChange = { viewModel.debounceSearch(it) },
        onBackPressed = { onBackPressed() },
        onClickMap = { onSearchCurrentLocation(screen) },
        onClickResultSearch = { viewModel.saveCity(it); onSelectResult(screen, it) },
        onClearAllHistory = { viewModel.clearHistory() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    state: SearchState,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onBackPressed: () -> Unit = {},
    onClickMap: () -> Unit = {},
    onClickResultSearch: (CityEntity) -> Unit = {},
    onClearAllHistory: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            SearchByTextAppBar(
                text = text,
                placeholder = state.listPlaceholder,
                onTextChange = onTextChange,
                onClickBack = onBackPressed,
                onClickMap = onClickMap,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Title(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                        .fillMaxWidth(),
                    textTitle = stringResource(id = R.string.history),
                    textAction = stringResource(id = R.string.clear_all),
                    onClickAction = onClearAllHistory,
                )
            }

            if (state.listHistory.isEmpty()) {
                item {
                    EmptyList(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.no_history),
                    )
                }
            } else {
                item {
                    ListHistorySearch(
                        modifier = Modifier.fillMaxWidth(),
                        onClickHistoryItem = onClickResultSearch,
                        listHistory = state.listHistory,
                    )
                }
            }

            item {
                Title(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                        .fillMaxWidth(),
                    textTitle = stringResource(id = R.string.result),
                )
            }

            if (state.listResult.isEmpty()) {
                item {
                    EmptyList(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.no_result),
                    )
                }
            } else {
                items(items = state.listResult) { item ->
                    ItemResultSearch(
                        modifier = Modifier.fillMaxWidth(),
                        item = item,
                        onClickResultSearch = onClickResultSearch,
                    )
                }
            }
        }
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    textTitle: String,
    textAction: String = "",
    onClickAction: () -> Unit = {},
) {
    Row(
        modifier = modifier.height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = textTitle,
            style = MaterialTheme.typography.titleLarge,
        )

        if (textAction.isNotBlank()) {
            TextButton(onClick = onClickAction) {
                Text(text = textAction)
            }
        }
    }
}

@Composable
fun EmptyList(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier.height(60.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListHistorySearch(
    modifier: Modifier = Modifier,
    listHistory: List<CityEntity> = emptyList(),
    onClickHistoryItem: (CityEntity) -> Unit = {},
) {
    FlowRow(
        modifier = modifier.padding(horizontal = 10.dp),
        mainAxisSpacing = 10.dp,
    ) {
        listHistory.forEach {
            ElevatedAssistChip(
                onClick = {
                    onClickHistoryItem(it)
                },
                label = {
                    Text(text = "${it.name}, ${it.country}")
                },
            )
        }
    }
}

@Composable
fun ItemResultSearch(
    modifier: Modifier = Modifier,
    item: CityEntity,
    onClickResultSearch: (CityEntity) -> Unit = {},
) {
    Column(
        modifier = modifier
            .height(66.dp)
            .clickable {
                onClickResultSearch.invoke(item)
            },
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = item.name,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp),
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = item.country,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchByTextAppBar(
    modifier: Modifier = Modifier,
    placeholder: List<String> = emptyList(),
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onClickBack: () -> Unit = {},
    onClickMap: () -> Unit = {},
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clearFocusOnKeyboardDismiss(),
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            IconButton(onClick = onClickBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        trailingIcon = {
            IconButton(onClick = onClickMap) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            }
        },
        placeholder = {
            InfinityText(
                texts = placeholder,
                delayTime = 4000L,
                content = { targetState ->
                    Text(
                        text = targetState,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        },
    )
}