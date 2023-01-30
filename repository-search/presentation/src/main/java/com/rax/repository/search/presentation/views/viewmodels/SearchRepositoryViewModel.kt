package com.rax.repository.search.presentation.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rax.core.extensions.launchSafely
import com.rax.core.viewmodels.mapExceptionMessage
import com.rax.repository.search.domain.usecases.SearchRepositoryUseCase
import com.rax.repository.search.presentation.views.popups.SearchRepoPopUp
import com.rax.repository.search.presentation.views.states.SearchRepoViewState
import com.rax.repository.search.presentation.views.viewmodels.inputs.SearchRepositoryInputs
import com.rax.repository.search.presentation.views.viewmodels.outputs.SearchRepositoryOutputs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private val searchRepo: SearchRepositoryUseCase,
) : ViewModel(), SearchRepositoryInputs, SearchRepositoryOutputs {

    private val _viewState = MutableLiveData<SearchRepoViewState>()
    private val _popUp = MutableLiveData<SearchRepoPopUp?>()

    private var searchJob: Job? = null
    private var searchQuery: String? = null
    private fun exceptionHandler() =
        CoroutineExceptionHandler { _, throwable ->
            val message = throwable.mapExceptionMessage()
            _popUp.postValue(
                SearchRepoPopUp.Snackbar(
                    message = message
                )
            )
        }

    val inputs: SearchRepositoryInputs = this
    val outputs: SearchRepositoryOutputs = this

    override fun onViewCreated() {
        _viewState.value = SearchRepoViewState.Empty
    }

    override fun onSearchTextChanged(text: String) {
        searchQuery = text
        searchJob?.cancel()
        if (text.isEmpty()) {
            _viewState.value = SearchRepoViewState.Empty
            return
        }
        searchJob = viewModelScope.launchSafely(exceptionHandler()) {
            delay(VIEW_STATE_DEBOUNCE)
            val result = searchRepo(text).cachedIn(viewModelScope)
            _viewState.value = (_viewState.value as? SearchRepoViewState.SearchResults)?.let {
                it.copy(repositoriesStream = result)
            } ?: SearchRepoViewState.SearchResults(
                repositoriesStream = result
            )
        }
    }

    override fun onPopUpDismissed() {
        _popUp.value = null
    }

    //outputs
    override val popUp: LiveData<SearchRepoPopUp?>
        get() = _popUp
    override val viewState: LiveData<SearchRepoViewState>
        get() = _viewState

    private companion object {
        private const val VIEW_STATE_DEBOUNCE = 600L
    }
}