package com.alexandra.musicapp.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.R
import com.alexandra.musicapp.databinding.ArtistRowLayoutBinding
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.models.Catalog
import kotlinx.android.synthetic.main.artist_row_layout.view.*

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
        holder.itemView.artist_row_layout.setOnClickListener {
            currentResult.artistName
            Toast.makeText(holder.itemView.context, currentResult.artistName, Toast.LENGTH_LONG).show()
          //  val action = CatalogFragmentDirections.actionMusicAppFragmentToAlbumsFragment(currentResult.artistId.toString())
            holder.itemView.findNavController().navigate(R.id.fragment_albums)
        }
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