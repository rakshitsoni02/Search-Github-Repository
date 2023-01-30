package com.rax.repository.search.data.envelopes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryEnvelope(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "html_url")
    val repoUrl: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "owner")
    val owner: OwnerEnvelope
)