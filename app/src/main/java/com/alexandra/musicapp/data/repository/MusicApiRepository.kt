package com.alexandra.musicapp.data.repository

import com.alexandra.foodyapp.models.ArtistWork
import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.domain.repositories.CatalogRepository
import com.alexandra.musicapp.domain.models.Catalog
import com.alexandra.musicapp.domain.repositories.AlbumsRepository
import javax.inject.Inject

class MusicApiRepository @Inject constructor(
    private val musicApiService: MusicApiService
) : CatalogRepository, AlbumsRepository {

    override suspend fun searchArtists(searchQuery: Map<String, String>): Catalog {
        val response = musicApiService.searchArtists(searchQuery)
        if (response.isSuccessful)
            return response.body()!!
        return Catalog(0, emptyList())
    }

    override suspend fun searchAlbums(searchQuery: Map<String, String>): ArtistWork {
        val response = musicApiService.searchAlbums(searchQuery)
        if (response.isSuccessful)
            return response.body()!!
        return ArtistWork(0, emptyList())
    }
}