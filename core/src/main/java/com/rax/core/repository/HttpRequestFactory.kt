package com.rax.core.repository

import com.rax.core.repository.HttpRequestHeaders.APPLICATION_JSON
import com.rax.core.repository.HttpRequestHeaders.CONTENT_TYPE
import com.rax.core.repository.HttpRequestHeaders.REQUEST_ID
import com.rax.core.repository.exceptions.ApiException
import com.rax.core.repository.exceptions.ResponseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import okhttp3.MultipartBody.Part
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalStdlibApi::class)
class HttpRequestFactory @Inject constructor(
    private val moshi: Moshi,
    private val apiService: ApiService
) : HttpRequestFactoryType {

    override suspend fun create(
        transition: HttpTransition,
        headers: Map<String, String>?,
        parameters: String?,
        multipartBody: Part?
    ): HttpSuccessResponse {
        val response = when (transition.verb) {
            HttpVerb.GET -> get(headers, transition, parameters)
            else -> throw IllegalArgumentException("Http verb: ${transition.verb} not supported yet!")
        }
        return mapResponseOrLiftException(response)
    }

    private suspend fun get(
        headers: Map<String, String>?,
        httpTransition: HttpTransition,
        queryParams: String?
    ): Response<String> {
        val contentType = httpTransition.accept ?: APPLICATION_JSON
        val headerMap = hashMapOf(Pair(CONTENT_TYPE, contentType))
        headers?.let { headerMap.putAll(it) }

        val url = queryParams?.let { "${httpTransition.url}?$it" } ?: httpTransition.url

        return apiService.get(headerMap, url)
    }

    private fun mapResponseOrLiftException(response: Response<String>): HttpSuccessResponse {
        return if (!response.isSuccessful) {
            val envelope: ErrorEnvelope?
            try {
                val json = response.errorBody()?.string() ?: "{}"
                envelope = requireNotNull(moshi.adapter<ErrorEnvelope>().fromJson(json))
            } catch (_: IOException) {
                throw createException(null, response)
            } catch (_: JsonDataException) {
                throw createException(null, response)
            }
            throw createException(envelope, response)
        } else {
            val body =
                if (response.body().isNullOrEmpty()) "{}" else requireNotNull(response.body())
            val code = response.code()
            val headers = response.headers().toMap()
            HttpSuccessResponse(code, body, headers)
        }
    }
}

private fun createException(
    envelope: ErrorEnvelope?,
    response: Response<String>
): RuntimeException {
    val requestId = getRequestId(response)
    return if (envelope != null) {
        ApiException(response.code(), requestId, envelope)
    } else {
        ResponseException(response.code(), requestId)
    }
}

private fun getRequestId(response: Response<String>): String {
    return response.headers()[REQUEST_ID].orEmpty()
}
