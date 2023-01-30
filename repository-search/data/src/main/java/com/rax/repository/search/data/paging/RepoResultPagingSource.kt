package com.rax.repository.search.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rax.core.threading.DispatcherProviderType
import com.rax.repository.search.data.envelopes.RepositoryPageEnvelope
import com.rax.repository.search.data.mappers.RepositoryEnvelopeMapper
import com.rax.repository.search.data.network.RepoDataApiType
import com.rax.repository.search.domain.model.Repository
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.withContext

@OptIn(ExperimentalStdlibApi::class)
class RepoResultPagingSource constructor(
    private val apiType: RepoDataApiType,
    private val dispatcherProvider: DispatcherProviderType,
    private val searchQuery: String,
    private val moshi: Moshi
) : PagingSource<Int, Repository>() {

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private suspend fun searchRepositories(page: Int): List<Repository> {
        val responseBody = apiType.searchRepositories(query = searchQuery, page = page).body
        val responseEnvelope =
            requireNotNull(moshi.adapter<RepositoryPageEnvelope>().fromJson(responseBody))
        return responseEnvelope.resultItems.map(RepositoryEnvelopeMapper::mapFrom)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> =
        withContext(dispatcherProvider.io()) {
            try {
                val page = params.key ?: 1
                val result = searchRepositories(page = page)

                LoadResult.Page(
                    data = result,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (result.isEmpty()) null else page.plus(1),
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

    private companion object {
        const val DEFAULT_PAGE = 1
    }
}