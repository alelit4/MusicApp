package com.alexandra.musicapp.domain.repositories

import com.alexandra.musicapp.domain.models.Artist
import retrofit2.http.QueryMap

interface ArtistsRepository  {
    suspend fun searchArtists(@QueryMap searchQuery: Map<String, String>): List<Artist>
}