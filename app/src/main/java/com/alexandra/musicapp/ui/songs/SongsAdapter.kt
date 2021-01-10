package com.alexandra.musicapp.ui.songs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.data.mediaplayer.MediaPlayer
import com.alexandra.musicapp.data.ShareHandler
import com.alexandra.musicapp.databinding.RowLayoutSongBinding
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.ui.CustomDiffUtils
import kotlinx.android.synthetic.main.row_layout_song.view.*

class SongsAdapter(
    var songs: List<Song>,
    private val addFavoriteSong: AddFavoriteSong,
    private val fillFavoriteSongsButton: FillFavoriteSongsButton
) : RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    class SongsViewHolder(private val binding: RowLayoutSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SongsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutSongBinding.inflate(layoutInflater, parent, false)
                return SongsViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = songs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        return SongsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val currentSong = songs[position]
        holder.bind(currentSong)
        fillFavoriteSongsButton.fillButtonIfFavorite(currentSong, holder.itemView.icon_fav_empty)
        holder.itemView.icon_play.setOnClickListener {
            MediaPlayer.play(holder.itemView.context, currentSong.previewUrl)
        }
        holder.itemView.icon_share.setOnClickListener {
            ShareHandler.shareSong(currentSong.trackName, holder.itemView.context)
        }
        holder.itemView.icon_fav_empty.setOnClickListener{
            addFavoriteSong.onClick(currentSong)
            fillFavoriteSongsButton.fillButton(holder.itemView.icon_fav_empty)
        }
    }

    interface AddFavoriteSong {
        fun onClick(song: Song)
    }

    interface FillFavoriteSongsButton {
        fun fillButton(button: View)
        fun fillButtonIfFavorite(currentSong: Song, button: View)
    }

    fun setData(catalog: List<Song>) {
        val songsDiffUtil = CustomDiffUtils(this.songs, catalog)
        val diffUtilResult = DiffUtil.calculateDiff(songsDiffUtil)
        this.songs = catalog as MutableList<Song>
        diffUtilResult.dispatchUpdatesTo(this)
    }
}