package com.alexandra.musicapp.ui.songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alexandra.musicapp.R
import com.alexandra.musicapp.ui.albums.AlbumsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_songs.view.*

@AndroidEntryPoint
class SongsFragment : Fragment() {

    private val arguments by navArgs<SongsFragmentArgs>()

    private lateinit var mView: View
    private val songsViewModel: SongsViewModel by activityViewModels()

    private var columnCount = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_songs, container, false)
        setUpRecyclerView()
        return mView
    }

    private fun setUpRecyclerView() {
        mView.shimmer_songs.adapter = SongsAdapter(emptyList())
        mView.shimmer_songs.layoutManager = StaggeredGridLayoutManager(columnCount,
            StaggeredGridLayoutManager.VERTICAL)
        songsViewModel.songs.value = mutableListOf()
        songsViewModel.songs.observe(viewLifecycleOwner, { pagedList ->
            mView.shimmer_songs.adapter = SongsAdapter(pagedList)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songsViewModel.requestSongsByCollectionIdPaged(arguments.collectionId)
    }


}

