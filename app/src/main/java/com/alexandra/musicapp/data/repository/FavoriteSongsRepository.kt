package com.alexandra.musicapp.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.alexandra.musicapp.data.db.SongsDao
import com.alexandra.musicapp.data.entities.asDomainModel
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.domain.models.asEntity
import javax.inject.Inject

class FavoriteSongsRepository @Inject constructor(
    private val songsDao: SongsDao
) {

    fun getAllFavoriteSongs(): LiveData<List<Song>> {
        return songsDao.getAll().map { value ->
            value.asDomainModel()
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(song: Song) {
        songsDao.insert(song.asEntity())
    }

    suspend fun delete(song: Song) {
        songsDao.delete(song.asEntity())
    }

}

