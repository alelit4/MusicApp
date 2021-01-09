package com.alexandra.musicapp.ui.songs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.databinding.RowLayoutSongBinding
import com.alexandra.musicapp.domain.models.Song

class SongsAdapter(
    private val songs: List<Song>
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
    }

}