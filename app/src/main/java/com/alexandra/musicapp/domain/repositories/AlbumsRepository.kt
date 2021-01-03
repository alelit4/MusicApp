package com.alexandra.musicapp.domain.repositories

import com.alexandra.foodyapp.models.ArtistWork
import com.alexandra.musicapp.domain.models.Catalog
import retrofit2.Response
import retrofit2.http.QueryMap

interface AlbumsRepository  {
    suspend fun searchAlbums(@QueryMap searchQuery: Map<String, String>): ArtistWork
}