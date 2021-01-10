package com.alexandra.musicapp.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexandra.musicapp.data.db.Constants.Companion.SONGS_TABLE
import com.alexandra.musicapp.domain.models.Song
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = SONGS_TABLE)
@Parcelize
class SongEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("trackId")
    val trackId: Int,
    @SerializedName("trackName")
    val trackName: String,
    @SerializedName("artistId")
    val artistId: Int,
    @SerializedName("collectionId")
    val collectionId: Int,
    @SerializedName("artworkUrl100")
    val imageUrl: String,
    @SerializedName("isStreamable")
    val isStreamable: Boolean,
    @SerializedName("previewUrl")
    val previewUrl: String,
): Parcelable

fun SongEntity.asDomainModel(): Song {
    return Song(trackId, trackName, artistId, collectionId, imageUrl, isStreamable, previewUrl)
}

fun List<SongEntity>.asDomainModel(): List<Song> {
    return this.map { songEntity -> songEntity.asDomainModel() }
}
