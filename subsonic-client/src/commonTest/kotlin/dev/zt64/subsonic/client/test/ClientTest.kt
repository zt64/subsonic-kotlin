package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.client.SubsonicAuth
import dev.zt64.subsonic.client.SubsonicClient
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ClientTest {
    private val successResponse = """
        {
          "subsonic-response": {
            "status": "ok",
            "version": "1.16.1",
            "openSubsonic": true
          }
        }
    """.trimIndent()

    @Test
    fun testUrlSubpath() = runTest {
        var requestedPath: String? = null

        val client = SubsonicClient(
            baseUrl = "https://example.com/navidrome",
            auth = SubsonicAuth.Key("secret"),
            engine = MockEngine { req ->
                requestedPath = req.url.encodedPath
                respond(
                    content = successResponse,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        name = HttpHeaders.ContentType,
                        value = ContentType.Application.Json.toString()
                    )
                )
            }
        )

        client.use {
            it.ping()
        }

        assertEquals("/navidrome/rest/ping.view", requestedPath)
    }

    @Test
    fun testUrlSubpathTrailingSlash() = runTest {
        var requestedPath: String? = null

        val client = SubsonicClient(
            baseUrl = "https://example.com/navidrome/",
            auth = SubsonicAuth.Key("secret"),
            engine = MockEngine { req ->
                requestedPath = req.url.encodedPath
                respond(
                    content = successResponse,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        name = HttpHeaders.ContentType,
                        value = ContentType.Application.Json.toString()
                    )
                )
            }
        )

        client.use {
            it.ping()
        }

        assertEquals("/navidrome/rest/ping.view", requestedPath)
    }
}