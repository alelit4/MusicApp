package com.alexandra.musicapp.data.entities

import com.alexandra.musicapp.domain.models.Song
import com.google.gson.annotations.SerializedName

data class SongsResult(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val songs: List<SongEntity>
){
    fun asDomainModel(): List<Song> {
        return songs.map { songEntity -> songEntity.asDomainModel() }
    }
}