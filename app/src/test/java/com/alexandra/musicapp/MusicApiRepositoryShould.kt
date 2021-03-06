package com.alexandra.musicapp

import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.data.entities.*
import com.alexandra.musicapp.data.repositories.MusicApiRepository
import com.alexandra.musicapp.utils.QueriesHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MusicApiRepositoryShould {

    @Mock
    private lateinit var musicApiService: MusicApiService

    @Test
    fun search_artist_by_name() = runBlocking {
        val fakeArtistName = "anArtistName"
        val fakeArtistResult = createFakeArtistsResult(fakeArtistName)
        val fakeNetworkResult = createFakeNetworkResult(fakeArtistResult)
        val fakeSearchQuery = QueriesHelper.retrieveSearchArtistsQuery(
            fakeArtistName, 1
        )
        `when`(musicApiService.searchArtists(fakeSearchQuery))
            .thenReturn(fakeNetworkResult)

        val repository = MusicApiRepository(musicApiService)
        val response = repository.searchArtists(fakeSearchQuery)

        Assert.assertEquals(fakeArtistResult.asDomainModel(), response)
    }

    @Test
    fun search_album_by_artist_id() = runBlocking {
        val fakeArtistId = 1
        val fakeAlbumsResult = createFakeAlbumsResult(fakeArtistId)
        val fakeNetworkResult = createFakeNetworkResult(fakeAlbumsResult)
        val fakeSearchQuery = QueriesHelper.retrieveSearchArtistWorkQuery(
            fakeArtistId.toString(), 1
        )
        `when`(musicApiService.searchAlbums(fakeSearchQuery))
            .thenReturn(fakeNetworkResult)

        val repository = MusicApiRepository(musicApiService)
        val response = repository.searchAlbums(fakeSearchQuery)

        Assert.assertEquals(fakeAlbumsResult.asDomainModel(), response)
    }

    @Test
    fun search_songs_by_albumId() = runBlocking {
        val fakeAlbumId = 1
        val fakeSongsResult = createFakeSongsResult(fakeAlbumId)
        val fakeNetworkResult = createFakeNetworkResult(fakeSongsResult)
        val fakeSearchQuery = QueriesHelper.retrieveSearchSongsQuery(
            fakeAlbumId.toString(), 1
        )
        `when`(musicApiService.searchSongs(fakeSearchQuery))
            .thenReturn(fakeNetworkResult)

        val repository = MusicApiRepository(musicApiService)
        val response = repository.searchSongs(fakeSearchQuery)

        Assert.assertEquals(fakeSongsResult.asDomainModel(), response)
    }

    private fun createFakeArtistsResult(fakeArtistName: String): ArtistsResult {
        val fakeArtistEntity = ArtistEntity(1, 1, fakeArtistName, "anArtistType", "aGenreName")
        val fakeArtistEntityList = listOf(fakeArtistEntity)
        return ArtistsResult(fakeArtistEntityList.size, fakeArtistEntityList)
    }

    private fun createFakeAlbumsResult(fakeArtistId: Int): AlbumsResult {
        val aFakeAlbum =
            AlbumEntity(fakeArtistId, 1, "aCollectionName", "anImageUrl", "aReleaseDate")
        val otherFakeAlbum =
            AlbumEntity(fakeArtistId, 2, "aCollectionName", "anImageUrl", "aReleaseDate")
        val fakeAlbumList = listOf(aFakeAlbum, otherFakeAlbum)
        return AlbumsResult(fakeAlbumList.size, fakeAlbumList)
    }

    private fun createFakeSongsResult(fakeAlbumId: Int): SongsResult {
        val fakeSongEntity =
            SongEntity(1, "aTrackName", 1, fakeAlbumId, "aImageUrl", true, "aPreviewUrl")
        val fakeSongsEntityList = listOf(fakeSongEntity)
        return SongsResult(fakeSongsEntityList.size, fakeSongsEntityList)
    }

    private fun createFakeNetworkResult(fakeArtistWork: AlbumsResult) =
        Response.success(fakeArtistWork)

    private fun createFakeNetworkResult(fakeArtistResult: ArtistsResult) =
        Response.success(fakeArtistResult)

    private fun createFakeNetworkResult(fakeSongsResult: SongsResult) =
        Response.success(fakeSongsResult)
}