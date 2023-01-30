package com.rax.repository.search.data.network

import com.rax.core.repository.HttpRequestFactoryType
import com.rax.core.repository.HttpTransition
import com.rax.core.repository.HttpVerb
import com.rax.test.BuildConfig
import com.rax.test.MainCoroutineRule
import com.rax.test.repositories.HttpSuccessResponseFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepoDataApiTest {

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    private val requestFactory: HttpRequestFactoryType = mockk(relaxUnitFun = true)
    private val successResponse = HttpSuccessResponseFactory.create()


    private lateinit var api: RepoDataApi

    @Before
    fun setUp() {
        api = RepoDataApi(requestFactory, coroutineTestRule.dispatcherProvider)
    }

    @Test
    fun `WHEN searchRepositories invoked THEN verify expected GET request`() = runTest {
        // GIVEN
        val query = "query"
        val page = 1
        val expectedTransition = HttpTransition(
            verb = HttpVerb.GET,
            url = "${BuildConfig.BASE_URL_API}search/repositories?q=${query}&per_page=30&page=${page}"
        )
        coEvery { requestFactory.create(transition = expectedTransition) } returns successResponse
        // WHEN
        api.searchRepositories(query, page)
        // THEN
        coVerify { requestFactory.create(transition = expectedTransition) }
    }
}
