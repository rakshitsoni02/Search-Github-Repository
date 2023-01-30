package com.rax.repository.search.domain.repository

import androidx.paging.PagingData
import com.rax.repository.search.domain.model.Repository
import kotlinx.coroutines.flow.Flow

interface RepoDataRepositoryType {
    suspend fun searchRepositories(query: String): Flow<PagingData<Repository>>
}