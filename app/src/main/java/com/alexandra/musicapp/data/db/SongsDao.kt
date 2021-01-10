package com.alexandra.musicapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alexandra.musicapp.data.entities.SongEntity
import com.alexandra.musicapp.domain.models.Song

@Dao
interface SongsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: SongEntity)

    @Delete
    suspend fun delete(song: SongEntity)

    @Query("SELECT * FROM songs ORDER BY trackName ASC")
    fun getAll(): LiveData<List<SongEntity>>

    @Query("DELETE FROM songs")
    suspend fun deleteAll()
}