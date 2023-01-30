package com.rax.repository.search.presentation.views.screens.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rax.repository.search.presentation.views.popups.SearchRepoPopUp

@Composable
fun PopUp(
    popUp: SearchRepoPopUp?,
    snackbarHostState: SnackbarHostState,
    onPopUpDismissed: () -> Unit
) {
    when (popUp) {
        is SearchRepoPopUp.Snackbar -> {
            LaunchedEffect(snackbarHostState) {
                if (snackbarHostState.showSnackbar(popUp.message) == SnackbarResult.Dismissed) {
                    onPopUpDismissed.invoke()
                } else {
                    // no-op
                }
            }
        }
        else -> {
            // no-op
        }
    }
}