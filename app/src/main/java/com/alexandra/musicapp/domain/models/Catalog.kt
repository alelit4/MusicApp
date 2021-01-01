package com.alexandra.musicapp.domain.models

import com.google.gson.annotations.SerializedName

data class Catalog(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Artist>
)