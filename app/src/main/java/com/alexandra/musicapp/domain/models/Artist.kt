package com.alexandra.musicapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Artist(
    val identification: Identification,
    val amgArtistId: Int,
    val artistType: String,
    val primaryGenreName: String,
) {
    @Parcelize
    data class Identification(
        val artistId: Int,
        val artistName: String
    ) : Parcelable {
        companion object {
            val EMPTY = Identification(0, "")
        }
    }
}