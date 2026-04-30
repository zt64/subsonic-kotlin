package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.client.SubsonicAuth
import dev.zt64.subsonic.client.SubsonicClient
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val apiUrl = env("SUBSONIC_API_URL")
val username = env("SUBSONIC_USERNAME") ?: "abc"
val password = env("SUBSONIC_PASSWORD") ?: "xyz"

private val responses = mutableMapOf<String, String?>()
private val expectedQueryParams = mutableMapOf<String, Map<String, String?>>()

@OptIn(ExperimentalUuidApi::class)
val client by lazy {
    if (apiUrl != null) {
        SubsonicClient(
            baseUrl = apiUrl,
            auth = SubsonicAuth.Token(username, password)
        )
    } else {
        val apiKey = Uuid.generateV4().toHexString()
        SubsonicClient(
            engine = MockEngine { req ->
                val params = req.url.parameters

                if ("apiKey" in params) {
                    assertEquals(apiKey, params["apiKey"])
                } else {
                    assertEquals(username, params["u"])
                }

                assertEquals(params["v"], "1.16.1")
                assertEquals(params["c"], "subsonic-kotlin")
                assertEquals(params["f"], "json")

                val endpoint = req.url.segments.last()

                val expected = expectedQueryParams[endpoint]
                if (expected != null) {
                    expected.forEach { (k, v) ->
                        assertEquals(v, params[k])
                    }
                }

                if (endpoint in responses) {
                    val content = responses[endpoint]
                    val body = if (content == null) {
                        """
                        {
                            "subsonic-response": {
                                "status": "ok",
                                "version": "1.16.1",
                                "type": "AwesomeServerName",
                                "serverVersion": "0.1.3 (tag)",
                                "openSubsonic": true
                            }
                        }
                        """.trimIndent()
                    } else {
                        """
                            {
                                "subsonic-response": {
                                "status": "ok",
                                "version": "1.16.1",
                                "type": "AwesomeServerName",
                                "serverVersion": "0.1.3 (tag)",
                                "openSubsonic": true,
                                $content
                            }
                        }
                        """.trimIndent()
                    }

                    respond(
                        content = body,
                        status = HttpStatusCode.OK,
                        headers = headersOf(
                            HttpHeaders.ContentType,
                            ContentType.Application.Json.toString()
                        )
                    )
                } else {
                    error("Unhandled $endpoint")
                }
            },
            baseUrl = "subsonic.somewhere.xyz",
            auth = SubsonicAuth.Token(username, password)
        )
    }
}

expect fun env(name: String): String?

suspend fun <T> testEndpoint(
    endpoint: String,
    response: String? = null,
    expectedParams: Map<String, String?> = emptyMap(),
    call: suspend SubsonicClient.() -> T
): T? {
    responses["$endpoint.view"] = response
    expectedQueryParams["$endpoint.view"] = expectedParams
    return client.call().also { println(it) }
}