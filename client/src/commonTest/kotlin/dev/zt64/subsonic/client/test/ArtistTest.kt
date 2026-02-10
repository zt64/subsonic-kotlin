package dev.zt64.subsonic.client.test

import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ArtistTest {
    @Test
    fun testGetArtists() = runTest {
        testEndpoint(
            endpoint = "getArtists",
            response = """
                "artists": {
                  "ignoredArticles": "The An A Die Das Ein Eine Les Le La",
                  "index": [
                    {
                      "name": "C",
                      "artist": [
                        {
                          "id": "100000016",
                          "name": "CARNÚN",
                          "coverArt": "ar-100000016",
                          "albumCount": 1
                        },
                        {
                          "id": "100000027",
                          "name": "Chi.Otic",
                          "coverArt": "ar-100000027",
                          "albumCount": 0
                        }
                      ]
                    },
                    {
                      "name": "I",
                      "artist": [
                        {
                          "id": "100000013",
                          "name": "IOK-1",
                          "coverArt": "ar-100000013",
                          "albumCount": 1
                        }
                      ]
                    }
                  ]
                }
            """.trimIndent()
        ) { getArtists() }
    }

    @Test
    fun testGetArtist() = runTest {
        testEndpoint(
            endpoint = "getArtist",
            response = """
                "artist": {
                  "id": "37ec820ca7193e17040c98f7da7c4b51",
                  "name": "2 Mello",
                  "coverArt": "ar-37ec820ca7193e17040c98f7da7c4b51_0",
                  "albumCount": 1,
                  "userRating": 5,
                  "artistImageUrl": "https://demo.org/image.jpg",
                  "starred": "2017-04-11T10:42:50.842Z",
                  "musicBrainzId": "189002e7-3285-4e2e-92a3-7f6c30d407a2",
                  "sortName": "Mello (2)",
                  "roles": [
                    "artist",
                    "albumartist",
                    "composer"
                  ],
                  "album": [
                    {
                      "id": "200000002",
                      "parent": "100000002",
                      "album": "Colorsmoke EP",
                      "title": "Colorsmoke EP",
                      "name": "Colorsmoke EP",
                      "isDir": true,
                      "coverArt": "al-200000002",
                      "songCount": 12,
                      "created": "2021-02-23T04:24:48+00:00",
                      "duration": 4568,
                      "playCount": 1,
                      "artistId": "100000002",
                      "artist": "Synthetic",
                      "year": 2007,
                      "genre": "Electronic",
                      "userRating": 5,
                      "averageRating": 3,
                      "starred": "2021-02-22T05:51:53Z"
                    }
                  ]
                }
            """.trimIndent()
        ) {
            getArtist("37ec820ca7193e17040c98f7da7c4b51")
        }
    }

    @Test
    fun testGetArtistInfo() = runTest {
        testEndpoint(
            endpoint = "getArtistInfo",
            response = """
                "artistInfo": {
                    "musicBrainzId": "1",
                    "biography": "Empty biography",
                    "smallImageUrl": "http://localhost:8989/play/art/f20070e8e11611cc53542a38801d60fa/artist/2/thumb34.jpg",
                    "mediumImageUrl": "http://localhost:8989/play/art/2b9b6c057cd4bf21089ce7572e7792b6/artist/2/thumb64.jpg",
                    "largeImageUrl": "http://localhost:8989/play/art/e18287c23a75e263b64c31b3d64c1944/artist/2/thumb174.jpg"
                }
            """.trimIndent()
        ) {
            getArtistInfo("1")
        }
    }
}