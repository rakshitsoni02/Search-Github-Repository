package com.rax.repository.search.data.network

import com.rax.core.repository.HttpSuccessResponse

interface RepoDataApiType {
    suspend fun searchRepositories(query: String, page: Int): HttpSuccessResponse
}