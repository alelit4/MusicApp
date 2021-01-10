package com.alexandra.musicapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexandra.musicapp.data.db.Constants.Companion.DATABASE_NAME
import com.alexandra.musicapp.data.entities.SongEntity

@Database(entities = [SongEntity::class], version = 1, exportSchema = false)
abstract class MusicAppDatabase : RoomDatabase() {
    abstract fun songsDao(): SongsDao

    companion object {
        @Volatile
        private var INSTANCE: MusicAppDatabase? = null

        fun getDatabase(context: Context): MusicAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicAppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}