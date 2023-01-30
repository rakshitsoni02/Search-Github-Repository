package com.rax.repository.search.presentation.views.popups

sealed interface SearchRepoPopUp {

    data class Snackbar(val message: String) : SearchRepoPopUp
}