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

private const val USER_AGENT = "subsonic-kotlin (github.com/zt64/subsonic-kotlin)"
private const val API_VERSION = "1.16.1"
private const val CLIENT_NAME = "subsonic-kotlin"

/**
 * Subsonic API client
 *
 * Based off Open Subsonic API
 */
public class SubsonicClient(
    private val httpClient: HttpClient,
    private val json: Json,
    private val baseUrl: String,
    private val params: Map<String, String>
) : SubsonicApi by SubsonicApiImpl(httpClient, json, baseUrl, params) {
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

            val json = Json {
                ignoreUnknownKeys = true
            }

            val httpClientConfig: HttpClientConfig<*>.() -> Unit = {
                install(ContentNegotiation) { json(json) }
                install(UserAgent) { agent = userAgent }
                install(HttpTimeout)

                defaultRequest {
                    url {
                        takeFrom("$baseUrl/rest/")
                        params.forEach { (k, v) -> parameters[k] = v }
                    }
                }

                clientConfig()
            }

            val httpClient = if (engine != null) {
                HttpClient(engine, httpClientConfig)
            } else {
                HttpClient(httpClientConfig)
            }

            return SubsonicClient(httpClient, json, baseUrl, params)
        }
    }
}