package dev.zt64.subsonic.api.model

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SubsonicSerializationTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun testResponseSuccessList() {
        val payload = """
            {
              "subsonic-response": {
                "status": "ok",
                "version": "1.16.1",
                "openSubsonic": true,
                "numbers": {
                  "number": [1, 2, 3]
                }
              }
            }
        """.trimIndent()

        val response = json.decodeFromString(
            SubsonicResponse.serializer(ListSerializer(Int.serializer())),
            payload
        )

        val success = assertIs<SubsonicResponse.Success<List<Int>>>(response)
        assertEquals(listOf(1, 2, 3), success.data)
    }

    @Test
    fun testResponseError() {
        val payload = """
            {
              "subsonic-response": {
                "status": "failed",
                "error": {
                  "code": "40",
                  "message": "Wrong username or password."
                }
              }
            }
        """.trimIndent()

        val response = json.decodeFromString(
            SubsonicResponse.serializer(String.serializer()),
            payload
        )

        val error = assertIs<SubsonicResponse.Error>(response)
        assertEquals(SubsonicErrorCode.WRONG_USERNAME_OR_PASSWORD, error.error.code)
    }
}