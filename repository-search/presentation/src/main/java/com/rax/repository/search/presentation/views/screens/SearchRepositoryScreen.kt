package com.rax.repository.search.presentation.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.rax.repository.search.domain.entities.RepositoryEntity
import com.rax.repository.search.presentation.R
import com.rax.repository.search.presentation.views.popups.SearchRepoPopUp
import com.rax.repository.search.presentation.views.screens.components.PopUp
import com.rax.repository.search.presentation.views.states.SearchRepoViewState
import com.rax.repository.search.presentation.views.viewmodels.SearchRepositoryViewModel
import com.rax.ui.components.snackbars.AppSnackbarHost
import com.rax.ui.components.textinput.TopSearchBar
import com.rax.ui.components.theme.AppTheme
import com.rax.ui.components.theme.Grey80
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchRepositoryScreen(viewModel: SearchRepositoryViewModel) {
    val viewState by viewModel.outputs.viewState.observeAsState()
    val popUp by viewModel.outputs.popUp.observeAsState()

    SearchRepositoryScreen(
        viewState = viewState,
        popUp = popUp,
        onPopUpDismissed = viewModel.inputs::onPopUpDismissed,
        onSearchTextChanged = viewModel.inputs::onSearchTextChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchRepositoryScreen(
    viewState: SearchRepoViewState?,
    popUp: SearchRepoPopUp?,
    onPopUpDismissed: () -> Unit,
    onSearchTextChanged: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()

    Scaffold(
        snackbarHost = { AppSnackbarHost(snackbarHostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_overview).uppercase())
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                TopSearchBar(
                    placeholder = stringResource(id = R.string.search_repos),
                    onValueChanged = onSearchTextChanged
                )
                when (viewState) {
                    SearchRepoViewState.Error -> MessageState(stringResource(id = R.string.error_text))
                    SearchRepoViewState.Empty -> MessageState(stringResource(id = R.string.empty_result_text))
                    is SearchRepoViewState.SearchResults -> {
                        val results = viewState.repositoriesStream.collectAsLazyPagingItems()
                        SearchResultList(
                            entities = results,
                            lazyListState = lazyListState
                        )
                    }
                    else -> {
                        //no-op
                    }
                }
            }
        }
        PopUp(
            popUp = popUp,
            snackbarHostState = snackbarHostState,
            onPopUpDismissed = onPopUpDismissed
        )
    }
}

@Composable
private fun MessageState(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(56.dp),
            painter = painterResource(id = com.rax.shared.ui.components.R.drawable.ic_error_48x48),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = message,
            color = Grey80,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun SearchRepositoryScreenPreView() {
    AppTheme {
        SearchRepositoryScreen(
            viewState = SearchRepoViewState.SearchResults(
                flowOf(PagingData.from(sampleRepoList))
            ),
            onPopUpDismissed = {
                //no-op
            },
            onSearchTextChanged = {
                //no-op
            },
            popUp = SearchRepoPopUp.Snackbar("Mr. Error Test")
        )
    }
}

val sampleRepoList = listOf(
    RepositoryEntity(
        id = 1,
        title = "Repo title 1",
        ownerAvatarUrl = "https://via.placeholder.com/150/d83e34",
        ownerName = "Owner Name 1",
        description = "description 1 description 1 description 1 description 1 description 1 ",
        name = "Repo name 1",
        repoUrl = "https://via.placeholder.com/150/d83e34"
    ),
    RepositoryEntity(
        id = 2,
        title = "Repo title 2",
        ownerAvatarUrl = "https://via.placeholder.com/150/d83e34",
        ownerName = "Owner Name 2",
        description = "description 2",
        name = "Repo name 2",
        repoUrl = "https://via.placeholder.com/150/d83e34"
    ),
    RepositoryEntity(
        id = 3,
        title = "Repo title 3",
        ownerAvatarUrl = "https://via.placeholder.com/150/d83e34",
        ownerName = "Owner Name 3",
        description = "description 3",
        name = "Repo name 3",
        repoUrl = "https://via.placeholder.com/150/d83e34"
    ),
)

val sampleDataFlow = flowOf(PagingData.from(sampleRepoList))

