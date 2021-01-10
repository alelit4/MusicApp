package com.alexandra.musicapp.ui.songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alexandra.musicapp.R
import com.alexandra.musicapp.data.mediaplayer.CustomMediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_songs.view.*

@AndroidEntryPoint
class SongsFragment : Fragment() {

    private val arguments by navArgs<SongsFragmentArgs>()

    private lateinit var mView: View
    private val songsViewModel: SongsViewModel by activityViewModels()
    private val mAdapter: SongsAdapter by lazy { SongsAdapter(emptyList()) }

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
        mView.recycler_view_songs.adapter = mAdapter
        mView.recycler_view_songs.layoutManager = StaggeredGridLayoutManager(columnCount,
            StaggeredGridLayoutManager.VERTICAL)
        mView.recycler_view_songs.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }
        songsViewModel.songs.value = mutableListOf()
        songsViewModel.songs.observe(viewLifecycleOwner, { pagedList ->
            mAdapter.setData(pagedList)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songsViewModel.requestSongsByCollectionIdPaged(arguments.collectionId)
    }

    override fun onPause() {
        super.onPause()
        CustomMediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomMediaPlayer.stop()
    }
}

