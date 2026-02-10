package dev.zt64.subsonic.client

import dev.zt64.subsonic.api.SubsonicApi
import dev.zt64.subsonic.api.SubsonicApiImpl
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val USER_AGENT = "github.com/zt64/subsonic-kotlin"
private const val API_VERSION = "1.16.1"
private const val CLIENT_NAME = "subsonic-kotlin"

/**
 * Subsonic API client
 *
 * Based off Open Subsonic API
 */
public class SubsonicClient(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val params: Map<String, String>
) : SubsonicApi by SubsonicApiImpl(httpClient, baseUrl, params) {
    public companion object {
        /**
         * Create a SubsonicClient
         */
        public operator fun invoke(
            baseUrl: String,
            auth: SubsonicAuth,
            client: String = CLIENT_NAME,
            userAgent: String = USER_AGENT,
            engine: HttpClientEngine? = null,
            clientConfig: HttpClientConfig<*>.() -> Unit = {}
        ): SubsonicClient {
            val params = buildMap {
                put("f", "json")
                put("v", API_VERSION)
                put("c", client)

                when (auth) {
                    is SubsonicAuth.Key -> {
                        put("apiKey", auth.apiKey)
                    }

                    is SubsonicAuth.Token -> {
                        put("u", auth.username)
                        put("t", auth.token)
                        put("s", auth.salt)
                    }
                }
            }

            val config: HttpClientConfig<*>.() -> Unit = {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }

                install(UserAgent) {
                    agent = userAgent
                }

                install(HttpTimeout)

                defaultRequest {
                    contentType(ContentType.Application.Json)
                    url(baseUrl)

                    url {
                        parameters.appendAll(
                            parameters {
                                set("f", "json")
                                set("v", API_VERSION)
                                set("c", client)

                                when (auth) {
                                    is SubsonicAuth.Key -> {
                                        set("apiKey", auth.apiKey)
                                    }

                                    is SubsonicAuth.Token -> {
                                        set("u", auth.username)
                                        set("t", auth.token)
                                        set("s", auth.salt)
                                    }
                                }
                            }
                        )
                    }
                }
            }

            val httpClient = if (engine != null) {
                HttpClient(engine) {
                    config()
                    clientConfig()
                }
            } else {
                HttpClient {
                    config()
                    clientConfig()
                }
            }

            return SubsonicClient(httpClient, baseUrl, params)
        }
    }
}