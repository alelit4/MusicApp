package com.alexandra.musicapp.domain.models

data class Song(
    val trackId: Int,
    val trackName: String,
    val artistId: Int,
    val collectionId: Int,
    val imageUrl: String,
    val isStreamable: Boolean,
    val previewUrl: String,
)