package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.client.SubsonicClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlaylistTest {
    @Test
    fun testGetPlaylists() = runTest {
        testEndpoint(
            endpoint = "getPlaylists",
            response = """
                "playlists": {
                  "playlist": [
                    {
                      "id": "800000003",
                      "name": "random - admin - private (admin)",
                      "owner": "admin",
                      "public": false,
                      "created": "2021-02-23T04:35:38+00:00",
                      "changed": "2021-02-23T04:35:38+00:00",
                      "songCount": 43,
                      "duration": 17875
                    },
                    {
                      "id": "800000002",
                      "name": "random - admin - public (admin)",
                      "owner": "admin",
                      "public": true,
                      "created": "2021-02-23T04:34:56+00:00",
                      "changed": "2021-02-23T04:34:56+00:00",
                      "songCount": 43,
                      "duration": 17786
                    }
                  ]
                }
            """.trimIndent(),
            call = SubsonicClient::getPlaylists
        )
    }

    @Test
    fun testGetPlaylist() = runTest {
        testEndpoint(
            endpoint = "getPlaylist",
            response = """
                 "playlist": {
                  "id": "800000075",
                  "name": "testcreate",
                  "owner": "user",
                  "public": true,
                  "created": "2023-03-16T03:18:41+00:00",
                  "changed": "2023-03-16T03:18:41+00:00",
                  "songCount": 1,
                  "duration": 304,
                  "entry": [
                    {
                      "id": "300000060",
                      "parent": "200000002",
                      "title": "BrownSmoke",
                      "isDir": false,
                      "isVideo": false,
                      "type": "music",
                      "albumId": "200000002",
                      "album": "Colorsmoke EP",
                      "artistId": "100000002",
                      "artist": "Synthetic",
                      "coverArt": "300000060",
                      "duration": 304,
                      "bitRate": 20,
                      "bitDepth": 16,
                      "samplingRate": 44100,
                      "channelCount": 2,
                      "userRating": 5,
                      "averageRating": 5,
                      "track": 4,
                      "year": 2007,
                      "genre": "Electronic",
                      "size": 792375,
                      "discNumber": 1,
                      "suffix": "wma",
                      "contentType": "audio/x-ms-wma",
                      "path": "Synthetic/Synthetic_-_Colorsmoke_EP-20k217-2007/04-Synthetic_-_BrownSmokeYSBM20k22khS.wma"
                    }
                  ]
                }
            """.trimIndent()
        ) {
            getPlaylist("300000060")
        }
    }

    @Test
    fun testCreatePlaylist() = runTest {
        testEndpoint(
            endpoint = "createPlaylist",
            response = """"""
        ) {
            createPlaylist("Test Playlist", emptyList())
        }
    }

    @Test
    fun testDeletePlaylist() = runTest {
        testEndpoint("deletePlaylist") {
            deletePlaylist("CZDXgohio9aEBl6RwmT6g8")
        }
    }
}