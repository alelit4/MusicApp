package com.alexandra.musicapp.data.entities

import com.alexandra.musicapp.domain.models.Artist
import com.google.gson.annotations.SerializedName

class ArtistsResult(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    var results: List<ArtistEntity>
) {
    fun asDomainModel(): List<Artist> {
        return results.map { artistEntity -> artistEntity.asDomainModel() }
    }
}