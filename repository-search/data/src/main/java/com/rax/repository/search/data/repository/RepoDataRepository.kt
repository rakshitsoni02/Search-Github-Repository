package com.rax.repository.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rax.core.threading.DispatcherProviderType
import com.rax.repository.search.data.network.RepoDataApiType
import com.rax.repository.search.data.paging.RepoResultPagingSource
import com.rax.repository.search.domain.repository.RepoDataRepositoryType
import com.squareup.moshi.Moshi
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoDataRepository @Inject constructor(
    private val apiType: RepoDataApiType,
    private val moshi: Moshi,
    private val dispatcherProvider: DispatcherProviderType
) : RepoDataRepositoryType {

    override suspend fun searchRepositories(query: String) = withContext(dispatcherProvider.io()) {
        Pager(
            config = PagingConfig(
                pageSize = 30,
            ),
            pagingSourceFactory = {
                RepoResultPagingSource(
                    moshi = moshi,
                    dispatcherProvider = dispatcherProvider,
                    searchQuery = query,
                    apiType = apiType
                )
            }
        )
    }.flow
}