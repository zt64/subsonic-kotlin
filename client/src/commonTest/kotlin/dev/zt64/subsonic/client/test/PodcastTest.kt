package dev.zt64.subsonic.client.test

import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PodcastTest {
    @Test
    fun testGetPodcasts() = runTest {
        testEndpoint(
            endpoint = "getPodcasts",
            response = """
                "podcasts": {
                  "channel": [
                    {
                      "id": "7390",
                      "parent": "7389",
                      "isDir": "false",
                      "title": "Jonas Gahr Støre",
                      "album": "NRK – Hallo P3",
                      "artist": "Podcast",
                      "year": "2015",
                      "coverArt": "7389",
                      "size": "41808585",
                      "contentType": "audio/mpeg",
                      "suffix": "mp3",
                      "duration": "2619",
                      "bitRate": "128",
                      "isVideo": "false",
                      "created": "2015-09-07T20:07:31.000Z",
                      "artistId": "453",
                      "type": "podcast",
                      "streamId": "7410",
                      "channelId": "17",
                      "description": "Jonas Gahr Støre fra Arbeiderpartiet er med i dagens partilederutspørring i Hallo P3!",
                      "status": "completed",
                      "publishDate": "2015-09-07T15:29:00.000Z"
                    },
                    {
                      "id": "3",
                      "url": "https://example.com/404",
                      "status": "error",
                      "errorMessage": "Not Found"
                    }
                  ]
                }
            """.trimIndent()
        ) {
            getPodcasts()
        }
    }

    @Test
    fun getNewestPodcasts() = runTest {
        testEndpoint(
            endpoint = "getNewestPodcasts",
            response = """
                "newestPodcasts": {
                  "episode": [
                    {
                      "id": "7390",
                      "parent": "7389",
                      "isDir": "false",
                      "title": "Jonas Gahr Støre",
                      "album": "NRK – Hallo P3",
                      "artist": "Podcast",
                      "year": "2015",
                      "coverArt": "7389",
                      "size": "41808585",
                      "contentType": "audio/mpeg",
                      "suffix": "mp3",
                      "duration": "2619",
                      "bitRate": "128",
                      "isVideo": "false",
                      "created": "2015-09-07T20:07:31.000Z",
                      "artistId": "453",
                      "type": "podcast",
                      "streamId": "7410",
                      "channelId": "17",
                      "description": "Jonas Gahr Støre fra Arbeiderpartiet er med i dagens partilederutspørring i Hallo P3!",
                      "status": "completed",
                      "publishDate": "2015-09-07T15:29:00.000Z"
                    }
                  ]
                }
            """.trimIndent()
        ) { getNewestPodcasts() }
    }

    @Test
    fun testRefreshPodcasts() = runTest {
        testEndpoint("refreshPodcasts") {
            refreshPodcasts()
        }
    }

    @Test
    fun testDeletePodcast() = runTest {
        testEndpoint("deletePodcastChannel") {
            deletePodcastChannel("abc")
        }

        testEndpoint("deletePodcastEpisode") {
            deletePodcastEpisode("abc")
        }
    }

    @Test
    fun testDownloadPodcastEpisode() = runTest {
        testEndpoint("downloadPodcastEpisode") {
            downloadPodcastEpisode("123")
        }
    }
}