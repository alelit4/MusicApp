package com.alexandra.musicapp.ui.songs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.data.mediaplayer.CustomMediaPlayer
import com.alexandra.musicapp.databinding.RowLayoutSongBinding
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.ui.CustomDiffUtils
import kotlinx.android.synthetic.main.row_layout_song.view.*

class SongsAdapter(
    var songs: List<Song>
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
        val currentResult = songs[position]
        holder.bind(currentResult)
        holder.itemView.icon_play.setOnClickListener {
             CustomMediaPlayer.play(holder.itemView.context, currentResult.previewUrl)
        }
    }

    fun setData(catalog: List<Song>){
        val songsDiffUtil = CustomDiffUtils(this.songs, catalog)
        val diffUtilResult = DiffUtil.calculateDiff(songsDiffUtil)
        this.songs = catalog as MutableList<Song>
        diffUtilResult.dispatchUpdatesTo(this)
    }

}