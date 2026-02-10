package dev.zt64.subsonic.client.test

import dev.zt64.subsonic.client.SubsonicClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

class UserTest {
    @Test
    fun testGetUser() = runTest {
        testEndpoint(
            endpoint = "getUser",
            response = """
                "user": {
                  "folder": [
                      1,
                      3
                  ],
                  "username": "sindre",
                  "email": "sindre@activeobjects.no",
                  "scrobblingEnabled": "true",
                  "adminRole": "false",
                  "settingsRole": "true",
                  "downloadRole": "true",
                  "uploadRole": "false",
                  "playlistRole": "true",
                  "coverArtRole": "true",
                  "commentRole": "true",
                  "podcastRole": "true",
                  "streamRole": "true",
                  "jukeboxRole": "true",
                  "shareRole": "false"
                }
            """.trimIndent()
        ) { getUser("123") }
    }

    @Test
    fun testDeleteUser() = runTest {
        testEndpoint(
            endpoint = "deleteUser",
            response = ""
        ) {
            deleteUser(username)
        }
    }

    @Test
    fun testUpdateUser() = runTest {
        testEndpoint("updateUser") {
            updateUser(username, password, email, false)
        }
    }

    @Test
    fun testGetUsers() = runTest {
        testEndpoint(
            endpoint = "getUsers",
            response = """
                "users": {
                  "user": [
                    {
                      "folder": [
                          1,
                          3
                      ],
                      "username": "sindre",
                      "email": "sindre@activeobjects.no",
                      "scrobblingEnabled": "true",
                      "adminRole": "false",
                      "settingsRole": "true",
                      "downloadRole": "true",
                      "uploadRole": "false",
                      "playlistRole": "true",
                      "coverArtRole": "true",
                      "commentRole": "true",
                      "podcastRole": "true",
                      "streamRole": "true",
                      "jukeboxRole": "true",
                      "shareRole": "false"
                    }
                  ]
                }
            """.trimIndent()
        ) { getUsers() }
    }

    @Test
    fun testCreateUser() = runTest {
        testEndpoint("createUser") {
            createUser(username, password, email)
        }
    }

    @Test
    fun testChangePassword() = runTest {
        testEndpoint("changePassword") {
            changePassword(username, password)
        }
    }

    // Chat
    @Test
    fun testGetChatMessages() = runTest {
        testEndpoint(
            endpoint = "getChatMessages",
            response = """
                "chatMessages": {
                  "chatMessage": [
                    {
                      "username": "admin",
                      "time": 1678943707000,
                      "message": "Hello World"
                    }
                  ]
                }
            """.trimIndent()
        ) { getChatMessages() }
    }

    @Test
    fun testAddChatMessage() = runTest {
        testEndpoint("addChatMessage") {
            addChatMessage("Test message")
        }
    }

    // Shares
    @Test
    fun testGetShares() = runTest {
        testEndpoint(
            endpoint = "getShares",
            response = """
                
            """.trimIndent(),
            call = SubsonicClient::getShares
        )
    }

    @Test
    fun testCreateShare() = runTest {
        testEndpoint("createShare") {
            createShare(listOf("Wa5fzmngg4VgscnxP1c05u"), "test", Clock.System.now())
        }
    }

    @Test
    fun testUpdateShare() = runTest {
        val share = testEndpoint("createShare") {
            createShare(listOf("Wa5fzmngg4VgscnxP1c05u"), "test", Clock.System.now())
        }!!

        testEndpoint("updateShare") {
            updateShare(share.id, expires = Clock.System.now() + 5.days)
        }
    }

    @Test
    fun testDeleteShare() = runTest {
        val share = testEndpoint("createShare") {
            createShare(listOf("Wa5fzmngg4VgscnxP1c05u"), "test", Clock.System.now())
        }!!

        testEndpoint("deleteShare") {
            deleteShare(share.id)
        }
    }

    @Test
    fun testStar() = runTest {
        testEndpoint("star") { star("abc") }
    }

    @Test
    fun testUnstar() = runTest {
        testEndpoint("unstar") { unstar("abc") }
    }

    @Test
    fun testGetBookmarks() = runTest {
        testEndpoint("getBookmarks") {
            getBookmarks()
        }
    }

    @Test
    fun testBookmark() = runTest {
        testEndpoint("createBookmark") {
            createBookmark("abc", position = 0)
        }

        testEndpoint("deleteBookmark") {
            deleteBookmark("abc")
        }
    }
}