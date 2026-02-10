package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.client.SubsonicClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LibraryTest {
    @Test
    fun testGetMusicFolders() = runTest {
        testEndpoint(
            endpoint = "getMusicFolders",
            response = """
                "musicFolders": {
                  "musicFolder": [
                    {
                      "id": 1,
                      "name": "Music"
                    },
                    {
                      "id": 2,
                      "name": "Test"
                    }
                  ]
                }
            """.trimIndent()
        ) { getMusicFolders() }
    }

    @Test
    fun testGetIndexes() = runTest {
        testEndpoint(
            endpoint = "getIndexes",
            response = """
                "indexes": {
                  "lastModified": 1678943707000,
                  "ignoredArticles": "The An A Die Das Ein Eine Les Le La",
                  "index": [
                    {
                      "name": "A",
                      "artist": [
                        {
                          "id": "ar-1",
                          "name": "Artist 1"
                        }
                      ]
                    }
                  ]
                }
            """.trimIndent()
        ) {
            getIndexes()
        }
    }

    @Test
    fun testStartScan() = runTest {
        testEndpoint(
            endpoint = "startScan",
            response = """
                "scanStatus": {
                  "scanning": true,
                  "count": 1
                }
            """.trimIndent(),
            call = SubsonicClient::startScan
        )
    }

    @Test
    fun testGetScanStatus() = runTest {
        testEndpoint(
            endpoint = "getScanStatus",
            response = """
                "scanStatus": {
                  "scanning": true,
                  "count": 1
                }
            """.trimIndent(),
            call = SubsonicClient::getScanStatus
        )
    }
}