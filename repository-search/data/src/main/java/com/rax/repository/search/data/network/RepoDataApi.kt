package com.rax.repository.search.data.network

import com.rax.core.BuildConfig
import com.rax.core.repository.HttpRequestFactoryType
import com.rax.core.repository.HttpTransition
import com.rax.core.repository.HttpVerb
import com.rax.core.threading.DispatcherProviderType
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoDataApi @Inject constructor(
    private val httpRequestFactory: HttpRequestFactoryType,
    private val dispatcherProvider: DispatcherProviderType
) : RepoDataApiType {

    override suspend fun searchRepositories(query: String, page: Int) =
        withContext(dispatcherProvider.io()) {
            val httpTransition = HttpTransition(
                verb = HttpVerb.GET,
                url = "${BuildConfig.BASE_URL_API}search/repositories?q=${query}&per_page=30&page=${page}"
            )
            httpRequestFactory.create(httpTransition)
        }
}
