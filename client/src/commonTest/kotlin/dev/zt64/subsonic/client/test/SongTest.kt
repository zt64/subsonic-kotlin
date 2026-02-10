package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.api.model.MediaType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SongTest {
    @Test
    fun testGetSong() = runTest {
        testEndpoint(
            endpoint = "getSong",
            response = """
              "song": {
                  "id": "082f435a363c32c57d5edb6a678a28d4",
                  "parent": "e8a0685e3f3ec6f251649af2b58b8617",
                  "isDir": false,
                  "title": "\"polar expedition\"",
                  "album": "Live at The Casbah - 2005-04-29",
                  "artist": "The New Deal",
                  "track": 4,
                  "year": 2005,
                  "coverArt": "mf-082f435a363c32c57d5edb6a678a28d4_6410b3ce",
                  "size": 19866778,
                  "contentType": "audio/flac",
                  "suffix": "flac",
                  "starred": "2023-03-27T09:45:27Z",
                  "duration": 178,
                  "bitRate": 880,
                  "bitDepth": 16,
                  "samplingRate": 44100,
                  "channelCount": 2,
                  "path": "The New Deal/Live at The Casbah - 2005-04-29/04 - \"polar expedition\".flac",
                  "playCount": 8,
                  "played": "2023-03-26T22:27:46Z",
                  "discNumber": 1,
                  "created": "2023-03-14T17:51:22.112827504Z",
                  "albumId": "e8a0685e3f3ec6f251649af2b58b8617",
                  "artistId": "97e0398acf63f9fb930d7d4ce209a52b",
                  "type": "music",
                  "mediaType": "song",
                  "isVideo": false,
                  "bpm": 134,
                  "comment": "This is a song comment",
                  "sortName": "Polar expedition",
                  "musicBrainzId": "189002e7-3285-4e2e-92a3-7f6c30d407a2",
                  "genres": [
                    {
                      "name": "Hip-Hop"
                    },
                    {
                      "name": "East coast"
                    }
                  ],
                  "artists": [
                    {
                      "id": "ar-1",
                      "name": "Artist 1"
                    },
                    {
                      "id": "ar-2",
                      "name": "Artist 2"
                    }
                  ],
                  "displayArtist": "Artist 1 feat. Artist 2",
                  "albumArtists": [
                    {
                      "id": "ar-6",
                      "name": "Artist 6"
                    },
                    {
                      "id": "ar-7",
                      "name": "Artist 7"
                    }
                  ],
                  "displayAlbumArtist": "Artist 6 & Artist 7",
                  "contributors": [
                    {
                      "role": "composer",
                      "artist": {
                        "id": "ar-3",
                        "name": "Artist 3"
                      }
                    },
                    {
                      "role": "composer",
                      "artist": {
                        "id": "ar-4",
                        "name": "Artist 4"
                      }
                    },
                    {
                      "role": "lyricist",
                      "artist": {
                        "id": "ar-5",
                        "name": "Artist 5"
                      }
                    },
                    {
                      "role": "performer",
                      "subRole": "Bass",
                      "artist": {
                        "id": "ar-5",
                        "name": "Artist 5"
                      }
                    }
                  ],
                  "displayComposer": "Artist 3, Artist 4",
                  "moods": [
                    "slow",
                    "cool"
                  ],
                  "replayGain": {
                    "trackGain": 0.1,
                    "albumGain": 1.1,
                    "trackPeak": 9.2,
                    "albumPeak": 9,
                    "baseGain": 0
                  }
                }  
            """.trimIndent()
        ) { getSong("082f435a363c32c57d5edb6a678a28d4") }
    }

    @Test
    fun testGetGenres() = runTest {
        testEndpoint(
            endpoint = "getGenres",
            response = """
                "genres": {
                  "genre": [
                    {
                      "songCount": 1,
                      "albumCount": 1,
                      "value": "Punk"
                    },
                    {
                      "songCount": 4,
                      "albumCount": 1,
                      "value": "Dark Ambient"
                    },
                    {
                      "songCount": 6,
                      "albumCount": 1,
                      "value": "Noise"
                    },
                    {
                      "songCount": 11,
                      "albumCount": 1,
                      "value": "Electronica"
                    },
                    {
                      "songCount": 11,
                      "albumCount": 1,
                      "value": "Dance"
                    },
                    {
                      "songCount": 12,
                      "albumCount": 1,
                      "value": "Electronic"
                    },
                    {
                      "songCount": 20,
                      "albumCount": 1,
                      "value": "Hip-Hop"
                    }
                  ]
                }
            """.trimIndent()
        ) { getGenres() }
    }

    @Test
    fun testGetTopSongs() = runTest {
        testEndpoint(
            endpoint = "getTopSongs",
            response = """
                "topSongs": {
                  "song": [
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
                    },
                    {
                      "id": "300000055",
                      "parent": "200000002",
                      "title": "Red&GreenSmoke",
                      "isDir": false,
                      "isVideo": false,
                      "type": "music",
                      "albumId": "200000002",
                      "album": "Colorsmoke EP",
                      "artistId": "100000002",
                      "artist": "Synthetic",
                      "coverArt": "300000055",
                      "duration": 400,
                      "bitRate": 64,
                      "bitDepth": 16,
                      "samplingRate": 44100,
                      "channelCount": 2,
                      "track": 5,
                      "year": 2007,
                      "genre": "Electronic",
                      "size": 3209886,
                      "discNumber": 1,
                      "suffix": "mp3",
                      "contentType": "audio/mpeg",
                      "path": "Synthetic/Synthetic_-_Colorsmoke_EP-20k217-2007(1)/05-Synthetic_-_RedGreenSmokePM20k22khS_64kb.mp3"
                    }
                  ]
                }
            """.trimIndent()
        ) { getTopSongs("abc") }
    }

    @Test
    fun testGetRandomSongs() = runTest {
        testEndpoint(
            endpoint = "getRandomSongs",
            response = """
                "randomSongs": {
                  "song": [
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
                    },
                    {
                      "id": "300000055",
                      "parent": "200000002",
                      "title": "Red&GreenSmoke",
                      "isDir": false,
                      "isVideo": false,
                      "type": "music",
                      "albumId": "200000002",
                      "album": "Colorsmoke EP",
                      "artistId": "100000002",
                      "artist": "Synthetic",
                      "coverArt": "300000055",
                      "duration": 400,
                      "bitRate": 64,
                      "bitDepth": 16,
                      "samplingRate": 44100,
                      "channelCount": 2,
                      "track": 5,
                      "year": 2007,
                      "genre": "Electronic",
                      "size": 3209886,
                      "discNumber": 1,
                      "suffix": "mp3",
                      "contentType": "audio/mpeg",
                      "path": "Synthetic/Synthetic_-_Colorsmoke_EP-20k217-2007(1)/05-Synthetic_-_RedGreenSmokePM20k22khS_64kb.mp3"
                    }
                  ]
                }
            """.trimIndent()
        ) { getRandomSongs() }
    }

    @Test
    fun testGetLyrics() = runTest {
        // TODO: Fun fact getLyrics and getLyricsBySongId return two different schema
        testEndpoint(
            endpoint = "getLyricsBySongId",
            response = """
                "lyrics": {
                  "artist": "Metallica",
                  "title": "Blitzkrieg",
                  "value": "Let us have peace, let us have life\n\nLet us escape the cruel night\n\nLet us have time, let the sun shine\n\nLet us beware the deadly sign\n\n\n\nThe day is coming\n\nArmageddon's near\n\nInferno's coming\n\nCan we survive the blitzkrieg?\n\nThe blitzkrieg\n\nThe blitzkrieg\n\n\n\nSave us from fate, save us from hate\n\nSave ourselves before it's too late\n\nCome to our need, hear our plea\n\nSave ourselves before the earth bleeds\n\n\n\nThe day is dawning\n\nThe time is near\n\nAliens calling\n\nCan we survive the blitzkrieg?"
                }
            """.trimIndent()
        ) {
            getLyrics("YN6pPLdIqkkPBan84Wgb3Y")
        }
    }

    @Test
    fun testDownloadSong() = runTest {
        testEndpoint("download") {
            download("")
        }
    }

    @Test
    fun testGetCoverArt() = runTest {
        testEndpoint("getCoverArt") {
            getCoverArt("")
        }
    }

    @Test
    fun testHls() = runTest {
        testEndpoint("hls") {
            hls("abc")
        }
    }

    @Test
    fun testTranscode() = runTest {
        testEndpoint(
            endpoint = "getTranscodeDecision",
            response = """
                "transcodeDecision": {
                  "canDirectPlay": false,
                  "canTranscode": true,
                  "transcodeReason": ["AudioCodecNotSupported"],
                  "errorReason": "",
                  "transcodeParams": "0001-0005-004",
                  "sourceStream": {
                    "protocol": "http",
                    "container": "flac",
                    "codec": "flac",
                    "audioChannels": 6,
                    "audioBitrate": 3000000,
                    "audioProfile": "",
                    "audioSamplerate": 96000,
                    "audioBitdepth": 24
                  },
                  "transcodeStream": {
                    "protocol": "hls",
                    "container": "mp4",
                    "codec": "aac",
                    "audioChannels": 2,
                    "audioBitrate": 256000,
                    "audioProfile": "xHE-AAC",
                    "audioSamplerate": 48000,
                    "audioBitdepth": 16
                  }
                }
            """.trimIndent()
        ) {
            getTranscodeDecision("abc", MediaType.SONG)
        }

        testEndpoint(
            endpoint = "getTranscodeStream",
            response = "todo"
        ) { getTranscodeStream("abc", MediaType.SONG, 0, "abc") }
    }
}