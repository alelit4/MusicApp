package com.alexandra.musicapp.ui.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexandra.musicapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_music_catalog.view.*

@AndroidEntryPoint
class MusicCatalogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView =  inflater.inflate(R.layout.fragment_music_catalog, container, false)
        mView.shimmer_catalog.showShimmer()
        return mView
    }

}