package com.rax.repository.search.presentation.di

import com.rax.repository.search.data.network.*
import com.rax.repository.search.data.repository.RepoDataRepository
import com.rax.repository.search.domain.repository.RepoDataRepositoryType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

object RepoSearchModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface RepoDataRepositoryModule {
        @Binds
        fun bindRepoDataRepository(impl: RepoDataRepository): RepoDataRepositoryType
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface UserApiTypeModule {
        @Binds
        fun bindRepoDataApi(impl: RepoDataApi): RepoDataApiType
    }
}

