package com.rax.repository.search.presentation.views.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.rax.repository.search.domain.entities.RepositoryEntity
import com.rax.repository.search.presentation.R
import com.rax.repository.search.presentation.views.screens.components.RepoListItem
import com.rax.ui.components.spinner.Spinner
import com.rax.ui.components.theme.AppTheme
import com.rax.ui.components.theme.Grey80

@Composable
fun SearchResultList(
    entities: LazyPagingItems<RepositoryEntity>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, state = lazyListState) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        items(entities) { entity ->
            entity?.also {
                RepoListItem(
                    entity = entity
                )
            }
        }
        item {
            PagingState(entity = entities)
        }
    }
}

@Composable
fun PagingState(entity: LazyPagingItems<RepositoryEntity>) {

    when {
        entity.loadState.refresh.endOfPaginationReached or
                entity.loadState.append.endOfPaginationReached -> {
            Message(text = stringResource(id = R.string.end_of_result))
        }
        else -> {
            PageState(entity.loadState.append)
            PageState(entity.loadState.refresh)
        }
    }
}

@Composable
private fun PageState(state: LoadState) {
    when (state) {
        is LoadState.Error -> Message(text = stringResource(id = R.string.error_api_rate_limit))
        LoadState.Loading -> Spinner(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.loading)
        )
        is LoadState.NotLoading -> {
            //no-op
        }
    }
}

@Composable
private fun Message(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = text,
        color = Grey80,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelSmall
    )
}


@Preview(name = "SearchResultList")
@Composable
private fun SearchResultListPreview() {
    AppTheme {
        SearchResultList(
            entities = sampleDataFlow.collectAsLazyPagingItems(),
            lazyListState = rememberLazyListState()
        )
    }
}
