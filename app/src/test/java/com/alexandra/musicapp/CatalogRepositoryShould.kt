package com.alexandra.musicapp

import com.alexandra.musicapp.data.api.CatalogApiService
import com.alexandra.musicapp.data.repository.CatalogApiRepository
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.models.Catalog
import com.alexandra.musicapp.utils.Constants
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CatalogRepositoryShould {

    @Mock
    private lateinit var catalogApiService: CatalogApiService


    @Test
    fun search_artist_by_name() = runBlocking {

        val fakeArtist = Artist(1, 1, "anArtistName", "anArtistType", "aGenreName")
        val fakeArtistList = listOf(fakeArtist)
        val fakeCatalog = Catalog(fakeArtistList.size, fakeArtistList)
        val fakeNetworkResult = Response.success(fakeCatalog)
        val fakeQuery: HashMap<String, String> = HashMap()
        fakeQuery[Constants.QUERY_TERM] = "anArtistName"

        `when`(catalogApiService.searchArtists(fakeQuery))
            .thenReturn(fakeNetworkResult)
        val repository = CatalogApiRepository(catalogApiService)

        val response = repository.searchArtists(fakeQuery)
        Assert.assertTrue(response.isSuccessful)
        Assert.assertEquals(response, fakeNetworkResult)

    }

}