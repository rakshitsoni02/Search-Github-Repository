package com.rax.repository.search.data.mappers

import com.rax.repository.search.data.envelopes.OwnerEnvelope
import com.rax.repository.search.domain.model.Owner

object OwnerEnvelopeMapper {

    fun mapFrom(from: OwnerEnvelope): Owner {
        return Owner(
            id = from.id,
            name = from.name,
            avatarUrl = from.avatarUrl
        )
    }
}