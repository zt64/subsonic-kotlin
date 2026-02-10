package dev.zt64.subsonic.client.test

import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class InternetRadioStationTest {
    @Test
    fun testCreateInternetRadioStation() = runTest {
        testEndpoint(
            endpoint = "createInternetRadioStation"
        ) {
            createInternetRadioStation(
                streamUrl = "",
                name = "",
                homepageUrl = ""
            )
        }
    }

    @Test
    fun testDeleteInternetRadioStation() = runTest {
        testEndpoint(
            endpoint = "deleteInternetRadioStation"
        ) {
            deleteInternetRadioStation("")
        }
    }

    @Test
    fun testGetInternetRadioStations() = runTest {
        testEndpoint(
            endpoint = "getInternetRadioStations",
            response = """
                "internetRadioStations": {
                  "internetRadioStation": [
                    {
                      "id": "1",
                      "name": "HBR1.com - Dream Factory",
                      "streamUrl": "http://ubuntu.hbr1.com:19800/ambient.aac",
                      "homepageUrl": "http://www.hbr1.com/"
                    },
                    {
                      "id": "2",
                      "name": "HBR1.com - I.D.M. Tranceponder",
                      "streamUrl": "http://ubuntu.hbr1.com:19800/trance.ogg",
                      "homepageUrl": "http://www.hbr1.com/"
                    }
                  ]
                }
            """.trimIndent()
        ) {
            getInternetRadioStations()
        }
    }
}