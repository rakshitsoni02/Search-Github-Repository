package com.rax.repository.search.data.mappers

import com.rax.repository.search.data.envelopes.RepositoryEnvelope
import com.rax.repository.search.domain.model.Repository

object RepositoryEnvelopeMapper {

    fun mapFrom(from: RepositoryEnvelope): Repository {
        return Repository(
            id = from.id,
            name = from.name,
            fullName = from.fullName,
            description = from.description.orEmpty(),
            repoUrl = from.repoUrl,
            owner = OwnerEnvelopeMapper.mapFrom(from.owner)
        )
    }
}