package com.alexandra.musicapp.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.databinding.RowLayoutArtistBinding
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.ui.CustomDiffUtils
import kotlinx.android.synthetic.main.row_layout_artist.view.*

class CatalogAdapter: RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>(){

    private var artists = mutableListOf<Artist>()
    val catalogSize: MutableLiveData<Int> = MutableLiveData(0)

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
        val currentResult = artists[position]
        holder.bind(currentResult)
        holder.itemView.row_layout_artist.setOnClickListener {
            val action = MusicCatalogFragmentDirections.actionFragmentMusicCatalogToFragmentAlbums(
                currentResult.identification.artistId.toString()
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    fun setData(catalog: List<Artist>){
        val artistsDiffUtil = CustomDiffUtils(this.artists, catalog)
        val diffUtilResult = DiffUtil.calculateDiff(artistsDiffUtil)
        this.artists = catalog as MutableList<Artist>
        diffUtilResult.dispatchUpdatesTo(this)
        catalogSize.value = itemCount
    }

    fun addData(catalog: List<Artist>) {
        val oldSize = this.artists.size
        this.artists.addAll(catalog)
        notifyItemInserted(oldSize)
        catalogSize.value = itemCount
    }


}