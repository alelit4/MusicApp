package com.alexandra.musicapp.domain.repositories

import com.alexandra.musicapp.domain.models.Album
import retrofit2.http.QueryMap

interface AlbumsRepository  {
    suspend fun searchAlbums(@QueryMap searchQuery: Map<String, String>): List<Album>
}