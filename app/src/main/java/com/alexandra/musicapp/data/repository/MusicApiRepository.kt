package com.alexandra.musicapp.data.repository

import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.domain.repositories.AlbumsRepository
import com.alexandra.musicapp.domain.repositories.ArtistsRepository
import com.alexandra.musicapp.domain.repositories.SongsRepository
import javax.inject.Inject

class MusicApiRepository @Inject constructor(
    private val musicApiService: MusicApiService
): ArtistsRepository, AlbumsRepository, SongsRepository {

    override suspend fun searchArtists(searchQuery: Map<String, String>): List<Artist> {
        val response = musicApiService.searchArtists(searchQuery)
        if (response.isSuccessful)
            return response.body()!!.results
                .map { artistEntity -> artistEntity.asDomainModel() }
        return emptyList()
    }

    override suspend fun searchAlbums(searchQuery: Map<String, String>): List<Album> {
        val response = musicApiService.searchAlbums(searchQuery)
        if (response.isSuccessful) {
            val artistWork = response.body()!!.results
            return artistWork.filter { album -> album.collectionId != 0 }
                .map { albumEntity -> albumEntity.asDomainModel() }
        }
        return emptyList()
    }

    override suspend fun searchSongs(searchQuery: Map<String, String>): List<Song> {
        val response = musicApiService.searchSongs(searchQuery)
        if (response.isSuccessful) {
            val songs = response.body()!!.songs
            return songs.filter { song -> song.trackId != 0 }
                .map { songEntity -> songEntity.asDomainModel() }
        }
        return emptyList()
    }

}