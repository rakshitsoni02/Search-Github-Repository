package com.rax.repository.search.presentation.views.viewmodels.inputs

interface SearchRepositoryInputs {
    fun onViewCreated()
    fun onPopUpDismissed()
    fun onSearchTextChanged(text: String)
}
