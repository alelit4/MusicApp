package com.alexandra.foodyapp.models


import com.google.gson.annotations.SerializedName

data class ArtistWork(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    var results: List<Album>
)