package com.alexandra.musicapp.data.repository

import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.repositories.AlbumsRepository
import com.alexandra.musicapp.domain.repositories.ArtistsRepository
import javax.inject.Inject

class MusicApiRepository @Inject constructor(
    private val musicApiService: MusicApiService
) : ArtistsRepository, AlbumsRepository {

    override suspend fun searchArtists(searchQuery: Map<String, String>): List<Artist> {
        val response = musicApiService.searchArtists(searchQuery)
        if (response.isSuccessful)
            return response.body()!!.results
        return emptyList()
    }

    override suspend fun searchAlbums(searchQuery: Map<String, String>): List<Album> {
        val response = musicApiService.searchAlbums(searchQuery)
        if (response.isSuccessful) {
            var artistWork = response.body()!!.results
            return artistWork.filter { it.collectionId != 0 }
        }
        return emptyList()
    }
}