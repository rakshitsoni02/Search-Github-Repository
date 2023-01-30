package com.rax.repository.search.presentation.views.viewmodels.outputs

import androidx.lifecycle.LiveData
import com.rax.repository.search.presentation.views.popups.SearchRepoPopUp
import com.rax.repository.search.presentation.views.states.SearchRepoViewState

interface SearchRepositoryOutputs {
    val popUp: LiveData<SearchRepoPopUp?>
    val viewState: LiveData<SearchRepoViewState>
}
