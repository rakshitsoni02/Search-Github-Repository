package com.rax.repository.search.domain.usecases

import androidx.paging.PagingData
import com.rax.repository.search.domain.repository.RepoDataRepositoryType
import com.rax.test.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)

class SearchRepositoryUseCaseTest {

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    private val repoDataRepositoryType: RepoDataRepositoryType = mockk(relaxUnitFun = true)

    private val useCase: SearchRepositoryUseCase by lazy {
        SearchRepositoryUseCase(
            repoDataRepositoryType = this.repoDataRepositoryType,
            dispatcherProvider = coroutineTestRule.dispatcherProvider
        )
    }

    @Test
    fun `WHEN invoked THEN verify expected repository being called`() = runTest {
        // GIVEN
        val query = "test"
        coEvery { repoDataRepositoryType.searchRepositories(query) } returns flowOf(
            PagingData.from(
                listOf()
            )
        )
        // WHEN
        useCase.invoke(query)
        // THEN
        coVerify {
            repoDataRepositoryType.searchRepositories(query)
        }
    }
}