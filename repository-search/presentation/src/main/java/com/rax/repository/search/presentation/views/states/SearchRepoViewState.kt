package com.rax.repository.search.presentation.views.states

import androidx.paging.PagingData
import com.rax.repository.search.domain.entities.RepositoryEntity
import kotlinx.coroutines.flow.Flow

sealed interface SearchRepoViewState {

    object Error : SearchRepoViewState

    object Empty : SearchRepoViewState

    data class SearchResults(
        val repositoriesStream: Flow<PagingData<RepositoryEntity>>
    ) : SearchRepoViewState
}