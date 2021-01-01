package com.alexandra.musicapp.data.repository

import com.alexandra.musicapp.data.api.CatalogApiService
import com.alexandra.musicapp.domain.CatalogRepository
import com.alexandra.musicapp.domain.models.Catalog
import retrofit2.Response
import javax.inject.Inject

class CatalogApiRepository @Inject constructor(
    private val catalogApiService: CatalogApiService
): CatalogRepository {

    override suspend fun searchArtists(searchQuery: Map<String, String>): Response<Catalog> {
        return catalogApiService.searchArtists(searchQuery)
    }

}