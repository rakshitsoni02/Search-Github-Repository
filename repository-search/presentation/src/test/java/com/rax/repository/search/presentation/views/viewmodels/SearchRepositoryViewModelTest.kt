package com.rax.repository.search.presentation.views.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.rax.core.repository.exceptions.ApiException
import com.rax.repository.search.domain.usecases.SearchRepositoryUseCase
import com.rax.repository.search.presentation.views.popups.SearchRepoPopUp
import com.rax.repository.search.presentation.views.screens.sampleRepoList
import com.rax.repository.search.presentation.views.states.SearchRepoViewState
import com.rax.test.MainCoroutineRule
import com.rax.test.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SearchRepositoryViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    private val searchRepo = mockk<SearchRepositoryUseCase>(relaxUnitFun = true)

    private lateinit var viewModel: SearchRepositoryViewModel

    @Before
    fun setup() {
        viewModel = SearchRepositoryViewModel(
            searchRepo = searchRepo
        )
    }

    @Test
    fun `GIVEN data error WHEN onSearchTextChanged invoked THEN verify error popUp is emitted`() =
        runTest {
            // GIVEN
            val query = "test"
            val error = mockk<ApiException> {
                coEvery { responseCode } returns HttpURLConnection.HTTP_UNAUTHORIZED
            }
            coEvery { searchRepo.invoke(query) } throws error
            // WHEN
            viewModel.inputs.onSearchTextChanged(query)
            // THEN
            delay(600)
            assertEquals(
                SearchRepoPopUp.Snackbar(
                    message = "Something went wrong"
                ), viewModel.outputs.popUp.getOrAwaitValue()
            )
        }

    @Test
    fun `GIVEN repository data WHEN onSearchTextChanged invoked THEN verify correct viewState is emitted`() =
        runTest {
            // GIVEN
            val query = "test"
            val repositoryList = flowOf(PagingData.from(sampleRepoList))
            coEvery { searchRepo.invoke(query) } returns repositoryList
            // WHEN
            viewModel.inputs.onSearchTextChanged(query)
            // THEN
            delay(600)
            assertTrue(
                viewModel.outputs.viewState.getOrAwaitValue() is SearchRepoViewState.SearchResults
            )
            coVerify {
                searchRepo.invoke(query)
            }
        }
}