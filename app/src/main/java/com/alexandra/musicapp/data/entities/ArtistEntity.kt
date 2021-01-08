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
    var primaryGenreName: String?,
){
    fun  asDomainModel(): Artist {
        val musicType = primaryGenreName ?: "No genre available"
        val identification: Artist.Identification = Artist.Identification(artistId, artistName)
        return Artist(identification, amgArtistId, artistType, musicType)
    }
}