package com.alexandra.musicapp.domain.models

import com.alexandra.musicapp.data.entities.SongEntity

data class Song(
    val trackId: Int,
    val trackName: String,
    val artistId: Int,
    val collectionId: Int,
    val imageUrl: String,
    val isStreamable: Boolean,
    val previewUrl: String,
)

fun Song.asEntity(): SongEntity {
    return SongEntity(trackId, trackName, artistId, collectionId, imageUrl, isStreamable, previewUrl)
}
