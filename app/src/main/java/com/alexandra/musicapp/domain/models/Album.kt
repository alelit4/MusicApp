package com.alexandra.foodyapp.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class Album(
    @SerializedName("artistId")
    val artistId: Int,
    @SerializedName("collectionId")
    val collectionId: Int,
    @SerializedName("collectionName")
    val collectionName: String,
    @SerializedName("artworkUrl100")
    val image :String,
    @SerializedName("releaseDate")
    val releaseDate: Date,

    )