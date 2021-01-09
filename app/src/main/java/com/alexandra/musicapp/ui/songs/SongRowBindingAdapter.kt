package com.alexandra.musicapp.ui.songs

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.alexandra.musicapp.R

class SongRowBindingAdapter {

    companion object{

        @BindingAdapter("loadSongImageFromUrl")
        @JvmStatic
        fun loadSongImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(500)
                error(R.drawable.ic_music_catalog)
            }
        }
    }
}