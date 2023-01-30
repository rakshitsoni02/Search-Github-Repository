package com.rax.repository.search.data.envelopes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryPageEnvelope(
    @Json(name = "total_count")
    val totalCount: Int,
    @Json(name = "items")
    val resultItems: List<RepositoryEnvelope>
)