package com.alexandra.musicapp.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.databinding.ArtistRowLayoutBinding
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.models.Catalog

class CatalogAdapter: RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>(){

    private var catalog = emptyList<Artist>()

    class CatalogViewHolder(private val binding: ArtistRowLayoutBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist){
            binding.artist = artist
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CatalogViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ArtistRowLayoutBinding.inflate(layoutInflater, parent, false)
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
    }

    override fun getItemCount(): Int {
        return catalog.size
    }

    fun setData(newCatalog: Catalog){
        val artistsDiffUtil = CatalogDiffUtils(catalog, newCatalog.results)
        val diffUtilResult = DiffUtil.calculateDiff(artistsDiffUtil)
        catalog = newCatalog.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}