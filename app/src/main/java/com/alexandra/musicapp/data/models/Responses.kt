package com.alexandra.musicapp.data.models

import com.google.gson.annotations.SerializedName

data class Responses<T>(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<T>
)