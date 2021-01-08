package com.alexandra.musicapp.data.api

import com.alexandra.musicapp.data.entities.AlbumsResult
import com.alexandra.musicapp.data.entities.ArtistsResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MusicApiService {
    @GET("/search")
    suspend fun searchArtists(@QueryMap searchQuery: Map<String, String>): Response<ArtistsResult>

    @GET("/lookup")
    suspend fun searchAlbums(@QueryMap searchQuery: Map<String, String>): Response<AlbumsResult>
}