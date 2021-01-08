package com.alexandra.musicapp.domain.models

data class Artist(
    val identification: Identification,
    val amgArtistId: Int,
    val artistType: String,
    val primaryGenreName: String,
) {
    data class Identification(
        val artistId: Int,
        val artistName: String
    ) {
        companion object {
            val EMPTY = Identification(0, "")
        }
    }
}