package com.alexandra.musicapp.ui.albums

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.alexandra.musicapp.R

class AlbumRowBindingAdapter {

    companion object{

        @BindingAdapter("retrieveAlbumYear")
        @JvmStatic
        fun retrieveAlbumYear(textView: TextView, releaseDate: String ){
            val year = releaseDate.split("-")[0]
            textView.text = year
        }

        @BindingAdapter("loadAlbumImageFromUrl")
        @JvmStatic
        fun loadAlbumImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(500)
                error(R.drawable.ic_music_catalog)
            }
        }
    }
}