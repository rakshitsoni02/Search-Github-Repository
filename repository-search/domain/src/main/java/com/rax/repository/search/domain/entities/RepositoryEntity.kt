package com.rax.repository.search.domain.entities

data class RepositoryEntity(
    val id: Int,
    val title: String,
    val name: String,
    val description: String,
    val repoUrl: String,
    val ownerAvatarUrl: String,
    val ownerName: String,
)