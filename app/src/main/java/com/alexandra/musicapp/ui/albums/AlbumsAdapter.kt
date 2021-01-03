package com.alexandra.musicapp.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.databinding.RowLayoutAlbumBinding
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.ui.CustomDiffUtils

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private var albums =  mutableListOf<Album>()

    class AlbumViewHolder(private val binding: RowLayoutAlbumBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.album = album
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AlbumViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutAlbumBinding.inflate(layoutInflater, parent, false)
                return AlbumViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val currentAlbum = albums[position]
        holder.bind(currentAlbum)
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Hello", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    fun setData(albums: List<Album>) {
        val customDiffUtil = CustomDiffUtils(this.albums, albums)
        val diffUtilResult = DiffUtil.calculateDiff(customDiffUtil)
        this.albums = albums as MutableList<Album>
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun addData(catalog: List<Album>) {
        val index = this.albums.size
        this.albums.addAll(catalog)
        notifyItemInserted(index)
    }
}
