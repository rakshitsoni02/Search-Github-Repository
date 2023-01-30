package com.rax.repository.search.presentation.views

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.rax.repository.search.presentation.views.screens.SearchRepositoryScreen
import com.rax.repository.search.presentation.views.viewmodels.SearchRepositoryViewModel
import com.rax.ui.components.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRepositoryActivity : AppCompatActivity() {

    @get:VisibleForTesting
    internal val viewModel: SearchRepositoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                SearchRepositoryScreen(viewModel)
            }
        }
        viewModel.inputs.onViewCreated()
    }
}