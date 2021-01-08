package com.alexandra.musicapp.data.entities

import com.alexandra.musicapp.domain.models.Artist
import com.google.gson.annotations.SerializedName

class ArtistEntity (
    @SerializedName("amgArtistId")
    val amgArtistId: Int,
    @SerializedName("artistId")
    val artistId: Int,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artistType")
    val artistType: String,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String,
){
    fun  asDomainModel(): Artist {
        return Artist(amgArtistId, artistId, artistName, artistType, primaryGenreName)
    }
}