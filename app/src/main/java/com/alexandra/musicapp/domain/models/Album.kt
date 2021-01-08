package com.alexandra.musicapp.domain.models

data class Album(
    val artistId: Int,
    val collectionId: Int,
    val collectionName: String,
    val imageUrl: String,
    val releaseDate: String,
)