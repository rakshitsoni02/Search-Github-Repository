package com.rax.repository.search.data.envelopes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerEnvelope(
    @Json(name = "id")
    val id: Int,
    @Json(name = "login")
    val name: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
)