package com.alexandra.musicapp.domain.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.alexandra.musicapp.domain.models.Song

interface FavoriteSongsRepository {
    fun getAllFavoriteSongs(): LiveData<List<Song>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(song: Song)

    suspend fun delete(song: Song)
}