package com.alexandra.musicapp.data.entities

import com.alexandra.musicapp.domain.models.Song
import com.google.gson.annotations.SerializedName

data class SongEntity(
    @SerializedName("trackId")
    val trackId: Int,
    @SerializedName("trackName")
    val trackName: String,
    @SerializedName("artistId")
    val artistId: Int,
    @SerializedName("collectionId")
    val collectionId: Int,
    @SerializedName("artworkUrl100")
    val imageUrl: String,
    @SerializedName("isStreamable")
    val isStreamable: Boolean,
    @SerializedName("previewUrl")
    val previewUrl: String,
){
    fun  asDomainModel(): Song {
        return Song(trackId, trackName, artistId, collectionId, imageUrl, isStreamable, previewUrl)
    }
}
