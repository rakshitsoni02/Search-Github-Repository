package com.rax.repository.search.data.mappers

import com.rax.repository.search.data.envelopes.RepositoryEnvelope
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalStdlibApi::class)
class RepositoryEnvelopeMapperTest {

    private val moshi = Moshi.Builder()
        .build()

    @Test
    fun `map repository envelope to model`() {
        val jsonFile = ClassLoader.getSystemResource("repository_item.json")
        val json = jsonFile.readText()
        val envelope = requireNotNull(moshi.adapter<RepositoryEnvelope>().fromJson(json))
        val result = RepositoryEnvelopeMapper.mapFrom(envelope)

        assertEquals(1, result.id)
        assertEquals("test", result.name)
        assertEquals("test/name", result.fullName)
        assertEquals("url", result.repoUrl)
        assertEquals("description", result.description)
    }
}
