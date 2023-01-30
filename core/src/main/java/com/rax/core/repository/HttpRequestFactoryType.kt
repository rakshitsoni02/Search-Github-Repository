package com.rax.core.repository

import okhttp3.MultipartBody

interface HttpRequestFactoryType {

    suspend fun create(
        transition: HttpTransition,
        headers: Map<String, String>? = null,
        parameters: String? = null,
        multipartBody: MultipartBody.Part? = null
    ): HttpSuccessResponse
}
