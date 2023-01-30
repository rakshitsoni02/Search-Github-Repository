package com.rax.repository.search.domain.mappers

import com.rax.repository.search.domain.entities.RepositoryEntity
import com.rax.repository.search.domain.model.Repository

object RepositoryEntityMapper {

    fun mapFrom(repository: Repository): RepositoryEntity {
        return RepositoryEntity(
            id = repository.id,
            title = repository.name,
            name = repository.fullName,
            repoUrl = repository.repoUrl,
            description = repository.description,
            ownerAvatarUrl = repository.owner.avatarUrl,
            ownerName = repository.owner.name
        )
    }
}