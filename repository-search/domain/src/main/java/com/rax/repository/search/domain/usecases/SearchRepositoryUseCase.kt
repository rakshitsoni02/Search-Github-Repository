package com.rax.repository.search.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.rax.core.threading.DispatcherProviderType
import com.rax.core.usecase.UseCase
import com.rax.repository.search.domain.entities.RepositoryEntity
import com.rax.repository.search.domain.mappers.RepositoryEntityMapper
import com.rax.repository.search.domain.repository.RepoDataRepositoryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryUseCase @Inject constructor(
    private val repoDataRepositoryType: RepoDataRepositoryType,
    private val dispatcherProvider: DispatcherProviderType
) : UseCase<Flow<PagingData<RepositoryEntity>>, String>() {

    override suspend fun build(param: String): Flow<PagingData<RepositoryEntity>> {
        return repoDataRepositoryType.searchRepositories(query = param)
            .map { pageData ->
                pageData.map(RepositoryEntityMapper::mapFrom)
            }
            .flowOn(dispatcherProvider.default())
    }
}
