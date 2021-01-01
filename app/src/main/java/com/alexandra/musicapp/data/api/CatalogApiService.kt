package com.alexandra.musicapp.data.api

import com.alexandra.musicapp.domain.models.Catalog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CatalogApiService {

    @GET("/search")
    suspend fun searchArtists(@QueryMap searchQuery: Map<String, String>): Response<Catalog>

}