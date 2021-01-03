package com.alexandra.musicapp.data.api

import com.alexandra.musicapp.data.models.Responses
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.domain.models.Artist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MusicApiService {

    @GET("/search")
    suspend fun searchArtists(@QueryMap searchQuery: Map<String, String>): Response<Responses<Artist>>

    @GET("/lookup")
    suspend fun searchAlbums(@QueryMap searchQuery: Map<String, String>): Response<Responses<Album>>

}