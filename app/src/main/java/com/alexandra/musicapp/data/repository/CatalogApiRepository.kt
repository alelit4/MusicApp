package com.alexandra.musicapp.data.repository

import android.util.Log
import android.util.Log.ERROR
import com.alexandra.musicapp.data.api.CatalogApiService
import com.alexandra.musicapp.domain.CatalogRepository
import com.alexandra.musicapp.domain.models.Catalog
import javax.inject.Inject

class CatalogApiRepository @Inject constructor(
    private val catalogApiService: CatalogApiService
) : CatalogRepository {

    override suspend fun searchArtists(searchQuery: Map<String, String>): Catalog {
        val response = catalogApiService.searchArtists(searchQuery)
        if (response.isSuccessful)
            return response.body()!!
        return Catalog(0, emptyList())
    }
}