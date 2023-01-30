package com.rax.core.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun get(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String
    ): Response<String>

}
