package com.alexandra.musicapp

import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.data.models.Responses
import com.alexandra.musicapp.data.repository.MusicApiRepository
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.utils.Constants.Companion.ALBUM
import com.alexandra.musicapp.utils.Constants.Companion.ALL_ARTIST
import com.alexandra.musicapp.utils.Constants.Companion.ALL_ARTIST_TERM
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ATTRIBUTE
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ENTITY
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ID
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_TERM
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.collections.HashMap

@RunWith(MockitoJUnitRunner::class)
class MusicApiRepositoryShould {

    @Mock
    private lateinit var musicApiService: MusicApiService

    @Test
    fun search_artist_by_name() = runBlocking {

        val fakeArtist = Artist(1, 1, "anArtistName", "anArtistType", "aGenreName")
        val fakeArtistList = listOf(fakeArtist)
        val fakeArtistListResponse = Responses<Artist>(fakeArtistList.size, fakeArtistList)
        val fakeNetworkResult = Response.success(fakeArtistListResponse)
        val fakeQuery: HashMap<String, String> = HashMap()
        fakeQuery[QUERY_TERM] = "anArtistName"
        fakeQuery[QUERY_ENTITY] = ALL_ARTIST
        fakeQuery[QUERY_ATTRIBUTE] = ALL_ARTIST_TERM
        `when`(musicApiService.searchArtists(fakeQuery))
            .thenReturn(fakeNetworkResult)

        val repository = MusicApiRepository(musicApiService)
        val response = repository.searchArtists(fakeQuery)

        Assert.assertEquals(fakeArtistList, response)
    }

    @Test
    fun search_album_by_artist_id() = runBlocking {
        val fakeArtistId = 1
        val fakeAlbum = Album(fakeArtistId, 1, "aCollectionName", "anImageUrl", "aReleaseDate")
        val fakeAlbumList = listOf(fakeAlbum)
        val fakeArtistWork = Responses<Album>(fakeAlbumList.size, fakeAlbumList)
        val fakeNetworkResult = Response.success(fakeArtistWork)
        val fakeQuery: HashMap<String, String> = HashMap()
        fakeQuery[QUERY_ENTITY] = ALBUM
        fakeQuery[QUERY_ID] = fakeArtistId.toString()
        `when`(musicApiService.searchAlbums(fakeQuery))
            .thenReturn(fakeNetworkResult)

        val repository = MusicApiRepository(musicApiService)
        val response = repository.searchAlbums(fakeQuery)

        Assert.assertEquals(fakeAlbumList, response)
    }
}