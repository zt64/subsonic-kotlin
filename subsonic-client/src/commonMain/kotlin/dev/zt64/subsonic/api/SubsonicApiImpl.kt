package dev.zt64.subsonic.api

import dev.zt64.subsonic.api.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json
import kotlin.time.Instant

internal class SubsonicApiImpl(
    private val httpClient: HttpClient,
    private val json: Json,
    private val baseUrl: String,
    private val clientParams: Map<String, String>
) : SubsonicApi {
    private fun buildUrl(
        endpoint: String,
        params: Map<String, String?> = emptyMap(),
        includeAuth: Boolean = true
    ): String = buildUrl {
        takeFrom(baseUrl)
        appendPathSegments(endpoint.removePrefix("/").split('/'))

        if (includeAuth) {
            clientParams.forEach { (key, value) ->
                parameters.append(key, value)
            }
        }

        params.forEach { (key, value) ->
            if (value != null) parameters.append(key, value)
        }
    }.toString()

    /**
     * Base request function. Interacts with the httpClient
     */
    private suspend fun execute(
        endpoint: String,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        val res = httpClient.get("$endpoint.view", builder)

        when (res.status) {
            HttpStatusCode.Gone -> throw SubsonicException(
                "Endpoint '$endpoint' will not be implemented"
            )
            HttpStatusCode.NotImplemented -> throw SubsonicException(
                "Endpoint '$endpoint' is not implemented"
            )
        }

        return res
    }

    private suspend inline fun <reified T : Any> getBody(
        endpoint: String,
        noinline builder: HttpRequestBuilder.() -> Unit = {}
    ): T {
        return when (val res = execute(endpoint, builder).body<SubsonicResponse<T>>()) {
            is SubsonicResponse.Success -> res.data
            is SubsonicResponse.Empty -> throw SubsonicException(
                "Expected data but received empty response"
            )
            is SubsonicResponse.Error -> throw SubsonicException(res.error.message, res.error.code)
        }
    }

    private suspend fun get(endpoint: String, builder: HttpRequestBuilder.() -> Unit = {}) {
        val res = execute(endpoint, builder).body<SubsonicResponse<Nothing>>()
        if (res is SubsonicResponse.Error) {
            throw SubsonicException(res.error.message, res.error.code)
        }
    }

    private suspend fun getBytes(
        endpoint: String,
        builder: HttpRequestBuilder.() -> Unit = {
        }
    ): ByteArray {
        val res = execute(endpoint, builder)
        if (res.contentType() == ContentType.Application.Json) {
            val parsed = json.decodeFromString<SubsonicResponse<Nothing>>(res.bodyAsText())
            if (parsed is SubsonicResponse.Error) {
                throw SubsonicException(parsed.error.message, parsed.error.code)
            }
            error("Unexpected JSON response for binary endpoint")
        }
        return res.bodyAsBytes()
    }

    override suspend fun ping() {
        get("ping")
    }

    override suspend fun tokenInfo(): TokenInfo {
        return getBody("tokenInfo")
    }

    override suspend fun getLicense(): License {
        return getBody("getLicense")
    }

    override suspend fun getOpenSubsonicExtensions(): List<SubsonicExtension> {
        return getBody("getOpenSubsonicExtensions")
    }

    override suspend fun changePassword(username: String, password: String) {
        get("changePassword") {
            parameter("username", username)
            parameter("password", password)
        }
    }

    private fun HttpRequestBuilder.addRoles(roles: List<Role>) {
        if (roles.isNotEmpty()) {
            if (Role.ADMIN in roles) parameter("adminRole", true)
            if (Role.SETTINGS in roles) parameter("settingsRole", true)
            if (Role.STREAM in roles) parameter("streamRole", true)
            if (Role.JUKEBOX in roles) parameter("jukeboxRole", true)
            if (Role.DOWNLOAD in roles) parameter("downloadRole", true)
            if (Role.UPLOAD in roles) parameter("uploadRole", true)
            if (Role.PLAYLIST in roles) parameter("playlistRole", true)
            if (Role.COVER_ART in roles) parameter("coverArtRole", true)
            if (Role.COMMENT in roles) parameter("commentRole", true)
            if (Role.PODCAST in roles) parameter("podcastRole", true)
            if (Role.SHARE in roles) parameter("shareRole", true)
            if (Role.VIDEO_CONVERSION in roles) parameter("videoConversionRole", true)
        }
    }

    override suspend fun createUser(
        username: String,
        password: String,
        email: String,
        ldapAuthenticated: Boolean,
        roles: List<Role>,
        folders: List<String>
    ) {
        get("createUser") {
            parameter("username", username)
            parameter("password", password)
            parameter("email", email)
            parameter("ldapAuthenticated", ldapAuthenticated)

            addRoles(roles)

            folders.forEach {
                parameter("musicFolderId", it)
            }
        }
    }

    override suspend fun updateUser(
        username: String,
        password: String,
        email: String?,
        ldapAuthenticated: Boolean?,
        roles: List<Role>?,
        maxBitRate: Int?,
        folders: List<String>
    ) {
        get("updateUser") {
            parameter("username", username)
            parameter("password", password)
            parameter("email", email)
            parameter("ldapAuthenticated", ldapAuthenticated)

            addRoles(roles.orEmpty())

            folders.forEach {
                parameter("musicFolderId", it)
            }

            parameter("maxBitRate", maxBitRate)
        }
    }

    override suspend fun deleteUser(username: String) {
        get("deleteUser") {
            parameter("username", username)
        }
    }

    override suspend fun getUser(username: String): User {
        return getBody("getUser") {
            parameter("username", username)
        }
    }

    override suspend fun getUsers(): List<User> {
        return getBody("getUsers")
    }

    override suspend fun getDirectories(): List<MusicFolder> {
        return getBody("getMusicFolders")
    }

    override suspend fun getIndexes(musicFolderId: String?): ArtistIndexes {
        return getBody("getIndexes")
    }

    override suspend fun getDirectory(id: String): Directory {
        return getBody("getMusicDirectory") {
            parameter("id", id)
        }
    }

    override suspend fun getGenres(): List<Genre> = getBody("getGenres")

    override suspend fun getArtists(folder: String?): Artists {
        return getBody("getArtists") {
            parameter("musicFolderId", folder)
        }
    }

    override suspend fun getArtist(id: String): Artist {
        return getBody("getArtist") {
            parameter("id", id)
        }
    }

    override suspend fun getAlbum(id: String): Album {
        return getBody("getAlbum") {
            parameter("id", id)
        }
    }

    override suspend fun getSong(id: String): Song {
        return getBody("getSong") {
            parameter("id", id)
        }
    }

    override suspend fun getArtistInfo(
        id: String,
        maxSimilar: Int,
        includeNotPresent: Boolean
    ): ArtistInfo {
        return getBody("getArtistInfo") {
            parameter("id", id)
            parameter("count", maxSimilar)
            parameter("includeNotPresent", includeNotPresent)
        }
    }

    override suspend fun getArtistInfo(
        artist: Artist,
        maxSimilar: Int,
        includeNotPresent: Boolean
    ): ArtistInfo {
        return getArtistInfo(artist.id, maxSimilar, includeNotPresent)
    }

    override suspend fun getArtistInfoID3(
        id: String,
        maxSimilar: Int,
        includeNotPresent: Boolean
    ): ArtistInfo {
        return getBody("getArtistInfo") {
            parameter("id", id)
            parameter("count", maxSimilar)
            parameter("includeNotPresent", includeNotPresent)
        }
    }

    override suspend fun getAlbumInfo(id: String): AlbumInfo {
        return getBody("getAlbumInfo") {
            parameter("id", id)
        }
    }

    override suspend fun getAlbumInfoID3(id: String): AlbumInfo {
        return getBody("getAlbumInfo2") {
            parameter("id", id)
        }
    }

    override suspend fun getSimilarSongs(id: String, count: Int): List<Song> {
        return getBody("getSimilarSongs") {
            parameter("id", id)
        }
    }

    override suspend fun getSimilarSongsID3(id: String, count: Int): List<Song> {
        return getBody("getSimilarSongs2") {
            parameter("id", id)
        }
    }

    override suspend fun getTopSongs(artist: String, count: Int): List<Song> {
        return getBody("getTopSongs") {
            parameter("artist", artist)
            parameter("count", count)
        }
    }

    override suspend fun getTopSongs(artist: Artist, count: Int): List<Song> {
        return getTopSongs(artist.name, count)
    }

    private suspend fun getAlbums(
        endpoint: String,
        type: AlbumListType,
        size: Int,
        offset: Int
    ): List<Album> {
        return getBody(endpoint) {
            parameter("type", type.value)
            parameter("size", size)
            parameter("offset", offset)

            when (type) {
                is AlbumListType.ByYear -> {
                    parameter("fromYear", type.fromYear)
                    parameter("toYear", type.toYear)
                }

                is AlbumListType.ByGenre -> {
                    parameter("genre", type.genre)
                }

                else -> {}
            }
        }
    }

    override suspend fun getAlbums(type: AlbumListType, size: Int, offset: Int): List<Album> {
        return getAlbums(
            endpoint = "getAlbumList",
            type = type,
            size = size,
            offset = offset
        )
    }

    override suspend fun getAlbumsID3(type: AlbumListType, size: Int, offset: Int): List<Album> {
        return getAlbums(
            endpoint = "getAlbumList2",
            type = type,
            size = size,
            offset = offset
        )
    }

    override suspend fun getRandomSongs(
        size: Int,
        genre: String?,
        fromYear: Int?,
        toYear: Int?
    ): List<Song> {
        return getBody("getRandomSongs") {
            parameter("size", size)
            parameter("genre", genre)
            parameter("fromYear", fromYear)
            parameter("toYear", toYear)
        }
    }

    override suspend fun getSongs(
        genre: String,
        count: Int,
        offset: Int,
        musicFolderId: String?
    ): List<Song> {
        return getBody("getSongsByGenre") {
            parameter("genre", genre)
            parameter("count", count)
            parameter("offset", offset)
            parameter("musicFolderId", musicFolderId)
        }
    }

    override suspend fun getNowPlaying(): List<NowPlayingEntry> {
        return getBody("nowPlaying")
    }

    override suspend fun getStarred(musicFolder: Directory?): Starred {
        return getBody("getStarred") {
            parameter("musicFolderId", musicFolder?.id)
        }
    }

    override suspend fun getStarredID3(musicFolder: Directory?): Starred {
        return getBody("getStarred2") {
            parameter("musicFolderId", musicFolder?.id)
        }
    }

    private suspend fun search(
        endpoint: String,
        query: String,
        artistCount: Int,
        artistOffset: Int,
        albumCount: Int,
        albumOffset: Int,
        songCount: Int,
        songOffset: Int,
        musicFolderId: Int?
    ): SearchResult {
        return getBody(endpoint) {
            parameter("query", query)
            parameter("artistCount", artistCount)
            parameter("artistOffset", artistOffset)
            parameter("albumCount", albumCount)
            parameter("albumOffset", albumOffset)
            parameter("songCount", songCount)
            parameter("songOffset", songOffset)
            parameter("musicFolderId", musicFolderId)
        }
    }

    override suspend fun search(
        query: String,
        artistCount: Int,
        artistOffset: Int,
        albumCount: Int,
        albumOffset: Int,
        songCount: Int,
        songOffset: Int,
        musicFolderId: Int?
    ): SearchResult {
        return search(
            endpoint = "search2",
            query = query,
            artistCount = artistCount,
            artistOffset = artistOffset,
            albumCount = albumCount,
            albumOffset = albumOffset,
            songCount = songCount,
            songOffset = songOffset,
            musicFolderId = musicFolderId
        )
    }

    override suspend fun searchID3(
        query: String,
        artistCount: Int,
        artistOffset: Int,
        albumCount: Int,
        albumOffset: Int,
        songCount: Int,
        songOffset: Int,
        musicFolderId: Int?
    ): SearchResult {
        return search(
            endpoint = "search3",
            query = query,
            artistCount = artistCount,
            artistOffset = artistOffset,
            albumCount = albumCount,
            albumOffset = albumOffset,
            songCount = songCount,
            songOffset = songOffset,
            musicFolderId = musicFolderId
        )
    }

    override suspend fun getPlaylists(): List<Playlist> = getBody("getPlaylists")

    override suspend fun getPlaylist(id: String): Playlist {
        return getBody("getPlaylist") {
            parameter("id", id)
        }
    }

    override suspend fun createPlaylist(name: String, songIds: List<String>): Playlist {
        return getBody("createPlaylist") {
            parameter("name", name)

            songIds.forEach { id ->
                parameter("songId", id)
            }
        }
    }

    override suspend fun createPlaylistFromSongs(name: String, songs: List<Song>): Playlist {
        return createPlaylist(name, songs.map(Song::id))
    }

    override suspend fun updatePlaylist(
        id: String,
        name: String?,
        comment: String?,
        public: Boolean?,
        songIdsToAdd: List<String>,
        songIndicesToRemove: List<Int>
    ) {
        get("updatePlaylist") {
            parameter("playlistId", id)
            parameter("name", name)
            parameter("comment", comment)
            parameter("public", public)

            songIdsToAdd.forEach {
                parameter("songIdToAdd", it)
            }

            songIndicesToRemove.forEach {
                parameter("songIndexToRemove", it)
            }
        }
    }

    override suspend fun deletePlaylist(id: String) {
        get("deletePlaylist") {
            parameter("id", id)
        }
    }

    override fun getStreamUrl(id: String, maxBitRate: Int, format: String?): String {
        return buildUrl(
            "rest/stream",
            buildMap {
                put("id", id)
                if (maxBitRate > 0) put("maxBitRate", maxBitRate.toString())
                if (format != null) put("format", format)
            }
        )
    }

    override suspend fun download(id: String): ByteReadChannel {
        // TODO: parse using subsonic response which is used for errors
        return httpClient.get("download") {
            parameter("id", id)
        }.bodyAsChannel()
    }

    override suspend fun hls(id: String, bitRate: Int?, audioTrack: String?): ByteArray {
        return getBytes("hls") {
            parameter("id", id)
            parameter("bitrate", bitRate)
            parameter("audioTrack", audioTrack)
        }
    }

    override suspend fun getCaptions(videoId: String, format: String): List<String> {
        return getBody("getCaptions") {
            parameter("id", videoId)
            parameter("format", format)
        }
    }

    override suspend fun getCoverArt(id: String, size: String?): ByteArray {
        return getBytes("getCoverArt") {
            parameter("id", id)
            parameter("size", size)
        }
    }

    override fun getCoverArtUrl(id: String, size: String?, auth: Boolean): String {
        return buildUrl(
            endpoint = "rest/getCoverArt",
            params = buildMap {
                put("id", id)
                if (size != null) put("size", size)
            },
            includeAuth = auth
        )
    }

    override suspend fun getLyrics(artist: String, title: String): Lyrics {
        return getBody("getLyrics") {
            parameter("artist", artist)
            parameter("title", title)
        }
    }

    override suspend fun getLyrics(id: String): List<StructuredLyrics> {
        return getBody("getLyricsBySongId") {
            parameter("id", id)
        }
    }

    override suspend fun getLyrics(song: Song): List<StructuredLyrics> = getLyrics(song.id)

    override suspend fun getAvatar(username: String): ByteArray {
        return getBytes("getAvatar") {
            parameter("username", username)
        }
    }

    override fun getAvatarUrl(username: String, auth: Boolean): String {
        return buildUrl(
            endpoint = "rest/getAvatar",
            params = mapOf("username" to username),
            includeAuth = auth
        )
    }

    override suspend fun star(vararg ids: String) {
        require(ids.isNotEmpty())

        get("star") {
            ids.forEach { parameter("id", it) }
        }
    }

    override suspend fun star(vararg items: SubsonicResource) {
        star(*items.map { it.id }.toTypedArray())
    }

    override suspend fun unstar(vararg ids: String) {
        require(ids.isNotEmpty())

        get("unstar") {
            ids.forEach { parameter("id", it) }
        }
    }

    override suspend fun unstar(vararg items: SubsonicResource) {
        unstar(*items.map { it.id }.toTypedArray())
    }

    override suspend fun setRating(id: String, rating: Int) {
        get("setRating") {
            parameter("id", id)
            parameter("rating", rating)
        }
    }

    override suspend fun scrobble(id: String, time: Instant, submission: Boolean) {
        get("scrobble") {
            parameter("id", id)
            parameter("time", time)
            parameter("submission", submission)
        }
    }

    override suspend fun getShares(): List<Share> {
        return getBody("getShares")
    }

    override suspend fun createShare(
        entries: List<String>,
        description: String?,
        expiresAt: Instant?
    ): Share {
        return getBody<List<Share>>("createShare") {
            entries.forEach { id ->
                parameter("id", id)
            }
            parameter("description", description)
            parameter("expires", expiresAt)
        }.single()
    }

    override suspend fun updateShare(id: String, description: String?, expiresAt: Instant?) {
        get("updateShare") {
            parameter("id", id)
            parameter("description", description)
            parameter("expires", expiresAt)
        }
    }

    override suspend fun deleteShare(id: String) {
        get("deleteShare") {
            parameter("id", id)
        }
    }

    override suspend fun getPodcasts(includeEpisodes: Boolean): List<PodcastChannel> {
        return getBody("getPodcasts") {
            parameter("includeEpisodes", includeEpisodes)
        }
    }

    override suspend fun getNewestPodcasts(count: Int): List<PodcastEpisode> {
        return getBody("getNewestPodcasts") {
            parameter("count", count)
        }
    }

    override suspend fun getPodcastEpisode(id: String) {
        return getBody("getPodcastEpisode") {}
    }

    override suspend fun refreshPodcasts() {
        get("refreshPodcasts")
    }

    override suspend fun createPodcastChannel(url: String) {
        get("createPodcastChannel") {
            parameter("url", url)
        }
    }

    override suspend fun deletePodcastChannel(id: String) {
        get("deletePodcastChannel") {
            parameter("id", id)
        }
    }

    override suspend fun deletePodcastEpisode(id: String) {
        get("deletePodcastEpisode") {
            parameter("id", id)
        }
    }

    override suspend fun downloadPodcastEpisode(id: String) {
        return getBody("downloadPodcastEpisode") {
            parameter("id", id)
        }
    }

    override suspend fun jukeboxControl(action: JukeboxAction, gain: Float) {
        return getBody("jukeboxControl") {
            parameter("action", action)

            when (action) {
                is JukeboxAction.Skip, is JukeboxAction.Skip -> {
                    parameter("index", action.index)
                }

                is JukeboxAction.Add -> {
                    parameter("id", action.id)
                }

                is JukeboxAction.Set -> {
                    parameter("id", action.id)
                }

                is JukeboxAction.SetGain -> {
                    parameter("gain", action.gain)
                }

                else -> {}
            }
        }
    }

    override suspend fun getInternetRadioStations(): List<InternetRadioStation> {
        return getBody("getInternetRadioStations")
    }

    override suspend fun createInternetRadioStation(
        streamUrl: String,
        name: String,
        homepageUrl: String?
    ) {
        return get("createInternetRadioStation") {
            parameter("streamUrl", streamUrl)
            parameter("name", name)
            parameter("homepageUrl", homepageUrl)
        }
    }

    override suspend fun updateInternetRadioStation(
        id: String,
        streamUrl: String,
        name: String,
        homepageUrl: String?
    ) {
        get("updateInternetRadioStation") {
            parameter("id", id)
            parameter("streamUrl", streamUrl)
            parameter("name", name)
            parameter("homepageUrl", homepageUrl)
        }
    }

    override suspend fun deleteInternetRadioStation(id: String) {
        get("deleteInternetRadioStation") {
            parameter("id", id)
        }
    }

    override suspend fun getChatMessages(): List<ChatMessage> {
        return getBody("getChatMessages")
    }

    override suspend fun addChatMessage(message: String) {
        get("addChatMessage") {
            parameter("message", message)
        }
    }

    override suspend fun getBookmarks(): List<Bookmark<SubsonicResource>> {
        return getBody("getBookmarks")
    }

    override suspend fun createBookmark(id: String, position: Long, comment: String?) {
        return get("createBookmark") {
            parameter("id", id)
            parameter("position", position)
            parameter("comment", comment)
        }
    }

    override suspend fun deleteBookmark(id: String) {
        get("deleteBookmark") {
            parameter("id", id)
        }
    }

    override suspend fun getPlayQueue(): PlayQueue {
        return getBody("getPlayQueue")
    }

    override suspend fun getPlayQueueByIndex() {
        return getBody("getPlayQueueByIndex")
    }

    override suspend fun savePlayQueue(id: Long?, currentId: Long, position: Int) {
        get("savePlayQueueByIndex") {
            parameter("id", id)
            parameter("current", currentId)
            parameter("position", position)
        }
    }

    override suspend fun savePlayQueue(id: String?, currentIndex: Int?, position: Long?) {
        get("savePlayQueueByIndex") {
            parameter("id", id)
            parameter("currentIndex", currentIndex)
            parameter("position", position)
        }
    }

    override suspend fun getScanStatus(): ScanStatus {
        return getBody("getScanStatus")
    }

    override suspend fun startScan(): ScanStatus {
        return getBody("startScan")
    }

    override suspend fun getTranscodeDecision(id: String, mediaType: MediaType): TranscodeDecision {
        return getBody("getTranscodeDecision") {
            parameter("id", id)
            parameter("mediaType", mediaType)
        }
    }

    override suspend fun getTranscodeStream(
        id: String,
        mediaType: MediaType,
        offset: Int,
        params: String
    ): ByteArray {
        return getBytes("getTranscodeStream") {
            parameter("id", id)
            parameter("mediaType", mediaType)
            parameter("offset", offset)
            parameter("transcodeParams", params)
        }
    }

    override suspend fun getTranscodeStream(
        id: String,
        mediaType: MediaType,
        offset: Int
    ): ByteReadChannel {
        val decision = getBody<TranscodeDecision>("getTranscodeDecision") {
            parameter("id", id)
            parameter("mediaType", mediaType)
        }

        return execute("getTranscodeStream") {
            parameter("id", id)
            parameter("mediaType", mediaType)
            parameter("offset", offset)
            parameter("transcodeParams", decision.transcodeParams)
        }.bodyAsChannel()
    }
}