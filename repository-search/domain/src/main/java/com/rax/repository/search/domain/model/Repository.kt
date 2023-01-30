package com.rax.repository.search.domain.model

data class Repository(
    val id: Int,
    val name: String,
    val fullName: String,
    val description: String,
    val repoUrl: String,
    val owner: Owner
)