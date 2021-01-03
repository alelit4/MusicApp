package com.alexandra.musicapp.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.databinding.RowLayoutArtistBinding
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.ui.CustomDiffUtils
import kotlinx.android.synthetic.main.row_layout_artist.view.*

class CatalogAdapter: RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>(){

    private var catalog = emptyList<Artist>()

    class CatalogViewHolder(private val binding: RowLayoutArtistBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist){
            binding.artist = artist
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CatalogViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutArtistBinding.inflate(layoutInflater, parent, false)
                return CatalogViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val currentResult = catalog[position]
        holder.bind(currentResult)
        holder.itemView.row_layout_artist.setOnClickListener {
            currentResult.artistName
            Toast.makeText(holder.itemView.context, currentResult.artistName, Toast.LENGTH_LONG).show()
            val action = MusicCatalogFragmentDirections.actionFragmentMusicCatalogToFragmentAlbums(currentResult.artistId.toString())
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return catalog.size
    }

    fun setData(catalog: List<Artist>){
        val artistsDiffUtil = CustomDiffUtils(this.catalog, catalog)
        val diffUtilResult = DiffUtil.calculateDiff(artistsDiffUtil)
        this.catalog = catalog
        diffUtilResult.dispatchUpdatesTo(this)
    }
}