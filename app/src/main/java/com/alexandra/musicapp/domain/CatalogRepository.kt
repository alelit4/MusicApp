package com.alexandra.musicapp.domain

import com.alexandra.musicapp.domain.models.Catalog
import retrofit2.Response
import retrofit2.http.QueryMap

interface CatalogRepository  {
    suspend fun searchArtists(@QueryMap searchQuery: Map<String, String>): Response<Catalog>
}