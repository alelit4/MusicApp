package com.alexandra.musicapp.domain.models

import com.google.gson.annotations.SerializedName

data class Artist (
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
)