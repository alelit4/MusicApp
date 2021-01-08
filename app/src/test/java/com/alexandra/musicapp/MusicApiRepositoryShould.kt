package com.alexandra.musicapp

import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.data.entities.AlbumEntity
import com.alexandra.musicapp.data.entities.AlbumsResult
import com.alexandra.musicapp.data.entities.ArtistEntity
import com.alexandra.musicapp.data.entities.ArtistsResult
import com.alexandra.musicapp.data.repository.MusicApiRepository
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
        val fakeSearchQuery =  QueriesHelper.retrieveSearchArtistWorkQuery(
            fakeArtistId.toString(), 1
        )
        `when`(musicApiService.searchAlbums(fakeSearchQuery))
            .thenReturn(fakeNetworkResult)

        val repository = MusicApiRepository(musicApiService)
        val response = repository.searchAlbums(fakeSearchQuery)

        Assert.assertEquals(fakeAlbumsResult.asDomainModel(), response)
    }

    private fun createFakeNetworkResult(fakeArtistResult: ArtistsResult) =
        Response.success(fakeArtistResult)

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
        val fakeArtistWork = AlbumsResult(fakeAlbumList.size, fakeAlbumList)
        return fakeArtistWork
    }

    private fun createFakeNetworkResult(fakeArtistWork: AlbumsResult) =
        Response.success(fakeArtistWork)
}