package com.rax.repository.search.data.envelopes

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalStdlibApi::class)
class RepositoryEnvelopeTest {

    private val moshi = Moshi.Builder()
        .build()

    @Test
    fun `parse repository json to envelope`() {
        val jsonFile = ClassLoader.getSystemResource("repository_item.json")
        val json = jsonFile.readText()

        val envelope = requireNotNull(moshi.adapter<RepositoryEnvelope>().fromJson(json))

        assertEquals(1, envelope.id)
        assertEquals("test", envelope.name)
        assertEquals("test/name", envelope.fullName)
        assertEquals("url", envelope.repoUrl)
        assertEquals("description", envelope.description)
    }
}
