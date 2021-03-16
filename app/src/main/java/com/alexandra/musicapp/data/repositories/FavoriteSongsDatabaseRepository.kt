package com.alexandra.musicapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.alexandra.musicapp.data.db.SongsDao
import com.alexandra.musicapp.data.entities.asDomainModel
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.domain.models.asEntity
import com.alexandra.musicapp.domain.repositories.FavoriteSongsRepository
import javax.inject.Inject

class FavoriteSongsDatabaseRepository @Inject constructor(
    private val songsDao: SongsDao
) : FavoriteSongsRepository {

    override fun getAllFavoriteSongs(): LiveData<List<Song>> {
        return songsDao.getAll().map { value ->
            value.asDomainModel()
        }
    }

    override suspend fun insert(song: Song) {
        songsDao.insert(song.asEntity())
    }

    override suspend fun delete(song: Song) {
        songsDao.delete(song.asEntity())
    }
}

