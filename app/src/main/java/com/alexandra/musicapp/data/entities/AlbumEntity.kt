package com.alexandra.musicapp.data.entities

import com.alexandra.musicapp.domain.models.Album
import com.google.gson.annotations.SerializedName

class AlbumEntity(
    @SerializedName("artistId")
    val artistId: Int,
    @SerializedName("collectionId")
    val collectionId: Int,
    @SerializedName("collectionName")
    val collectionName: String,
    @SerializedName("artworkUrl100")
    val imageUrl: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
){
    fun  asDomainModel(): Album {
        return Album(artistId, collectionId, collectionName, imageUrl, releaseDate)
    }
}
