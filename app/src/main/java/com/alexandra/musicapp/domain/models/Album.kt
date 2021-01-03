package com.alexandra.musicapp.domain.models

import com.google.gson.annotations.SerializedName

data class Album(
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
)