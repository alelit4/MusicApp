package com.alexandra.musicapp.domain.repositories

import com.alexandra.musicapp.domain.models.Song
import retrofit2.http.QueryMap

interface SongsRepository  {
    suspend fun searchSongs(@QueryMap searchQuery: Map<String, String>): List<Song>
}