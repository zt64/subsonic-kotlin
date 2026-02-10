package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.client.SubsonicClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SystemTest {
    @Test
    fun testGetOpenSubsonicExtensions() = runTest {
        testEndpoint(
            endpoint = "getOpenSubsonicExtensions",
            response = """
                "openSubsonicExtensions": [
                    {
                        "name": "template",
                        "versions": [
                            1,
                            2
                        ]
                    },
                    {
                        "name": "transcodeOffset",
                        "versions": [
                            1
                        ]
                    }
                ]
            """.trimIndent(),
            call = SubsonicClient::getOpenSubsonicExtensions
        )
    }

    @Test
    fun testTokenInfo() = runTest {
        testEndpoint(
            endpoint = "tokenInfo",
            response = """
                "tokenInfo": {
                  "username": "$username"
                }
            """.trimIndent(),
            call = SubsonicClient::tokenInfo
        )
    }

    @Test
    fun testGetLicense() = runTest {
        testEndpoint(
            endpoint = "getLicense",
            response = """
                "license": {
                    "valid": true,
                    "email": "$email",
                    "licenseExpires": "2017-04-11T10:42:50.842Z",
                    "trialExpires": "2017-04-11T10:42:50.842Z"
                }
            """.trimIndent(),
            call = SubsonicClient::getLicense
        )
    }
}