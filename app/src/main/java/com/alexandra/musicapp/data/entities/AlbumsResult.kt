package com.alexandra.musicapp.data.entities

import com.alexandra.musicapp.domain.models.Album
import com.google.gson.annotations.SerializedName

class AlbumsResult(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<AlbumEntity>
){
    fun asDomainModel(): List<Album> {
        return results.map { albumEntity -> albumEntity.asDomainModel() }
    }
}