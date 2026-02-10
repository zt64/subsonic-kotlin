package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.api.model.AlbumListType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AlbumTest {
    @Test
    fun testGetAlbum() = runTest {
        testEndpoint(
            endpoint = "getAlbum",
            response = """
                "album": {
                  "id": "200000021",
                  "parent": "100000036",
                  "album": "Forget and Remember",
                  "title": "Forget and Remember",
                  "name": "Forget and Remember",
                  "isDir": true,
                  "coverArt": "al-200000021",
                  "songCount": 20,
                  "created": "2021-07-22T02:09:31+00:00",
                  "duration": 4248,
                  "playCount": 0,
                  "artistId": "100000036",
                  "artist": "Comfort Fit",
                  "year": 2005,
                  "genre": "Hip-Hop",
                  "song": [
                    {
                      "id": "300000116",
                      "parent": "200000021",
                      "title": "Can I Help U?",
                      "isDir": false,
                      "isVideo": false,
                      "type": "music",
                      "albumId": "200000021",
                      "album": "Forget and Remember",
                      "artistId": "100000036",
                      "artist": "Comfort Fit",
                      "coverArt": "300000116",
                      "duration": 103,
                      "bitRate": 216,
                      "bitDepth": 16,
                      "samplingRate": 44100,
                      "channelCount": 2,
                      "track": 1,
                      "year": 2005,
                      "genre": "Hip-Hop",
                      "size": 2811819,
                      "discNumber": 1,
                      "suffix": "mp3",
                      "contentType": "audio/mpeg",
                      "path": "user/Comfort Fit/Forget And Remember/1 - Can I Help U?.mp3"
                    },
                    {
                      "id": "300000121",
                      "parent": "200000021",
                      "title": "Planetary Picknick",
                      "isDir": false,
                      "isVideo": false,
                      "type": "music",
                      "albumId": "200000021",
                      "album": "Forget and Remember",
                      "artistId": "100000036",
                      "artist": "Comfort Fit",
                      "coverArt": "300000121",
                      "duration": 358,
                      "bitRate": 238,
                      "bitDepth": 16,
                      "samplingRate": 44100,
                      "channelCount": 2,
                      "track": 2,
                      "year": 2005,
                      "genre": "Hip-Hop",
                      "size": 10715592,
                      "discNumber": 1,
                      "suffix": "mp3",
                      "contentType": "audio/mpeg",
                      "path": "user/Comfort Fit/Forget And Remember/2 - Planetary Picknick.mp3"
                    }
                  ]
                }
            """.trimIndent()
        ) {
            getAlbum("200000021")
        }
    }

    @Test
    fun testGetAlbumInfo() = runTest {
        testEndpoint(
            endpoint = "getAlbumInfo",
            response = """
                "albumInfo": {
                  "notes": "Download the full release here (creative commons). These cripsy beats are ripe with thumping funk and techno influences, sample wizardry and daring shuffles. Composed with the help of unique sound plugins which were especially programmed to measure Comfort Fit’s needs and wishes, we think the chances aren’t bad that you’ll fall for the unique sound signature, bounce and elegance of this unusual Hip Hop production. Ltj bukem / Good looking Rec., UK: \"Really love this music.\" Velanche / XLR8R, UK: \"Awesome job he's done... overall production is dope.\" Kwesi / BBE Music, UK: \"Wooooooowwwww... WHAT THE FUCK! THIS IS WHAT",
                  "musicBrainzId": "6e1d48f7-717c-416e-af35-5d2454a13af2",
                  "smallImageUrl": "http://localhost:8989/play/art/0f8c3cbd6b0b22c3b5402141351ac812/album/21/thumb34.jpg",
                  "mediumImageUrl": "http://localhost:8989/play/art/41b16680dc1b3aaf5dfba24ddb6a1712/album/21/thumb64.jpg",
                  "largeImageUrl": "http://localhost:8989/play/art/e6fd8d4e0d35c4436e56991892bfb27b/album/21/thumb174.jpg"
                }
            """.trimIndent()
        ) {
            getAlbumInfo("4vXlEeZr862A0WmK8wJXhc")
        }
    }

    @Test
    fun testGetAlbums() = runTest {
        testEndpoint(
            endpoint = "getAlbumList",
            response = """
                "albumList": {
                  "album": [
                    {
                      "id": "200000021",
                      "parent": "100000036",
                      "album": "Forget and Remember",
                      "title": "Forget and Remember",
                      "name": "Forget and Remember",
                      "isDir": true,
                      "coverArt": "al-200000021",
                      "songCount": 20,
                      "created": "2021-07-22T02:09:31+00:00",
                      "duration": 4248,
                      "playCount": 0,
                      "artistId": "100000036",
                      "artist": "Comfort Fit",
                      "year": 2005,
                      "genre": "Hip-Hop"
                    },
                    {
                      "id": "200000012",
                      "parent": "100000019",
                      "album": "Buried in Nausea",
                      "title": "Buried in Nausea",
                      "name": "Buried in Nausea",
                      "isDir": true,
                      "coverArt": "al-200000012",
                      "songCount": 9,
                      "created": "2021-02-24T01:44:21+00:00",
                      "duration": 1879,
                      "playCount": 0,
                      "artistId": "100000019",
                      "artist": "Various Artists",
                      "year": 2012,
                      "genre": "Punk"
                    }
                  ]
                }
            """.trimIndent()
        ) {
            getAlbums(AlbumListType.Random)
        }
    }
}