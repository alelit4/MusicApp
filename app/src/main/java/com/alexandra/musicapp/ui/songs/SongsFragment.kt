package com.alexandra.musicapp.ui.songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alexandra.musicapp.R
import com.alexandra.musicapp.data.mediaplayer.CustomMediaPlayer
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.ui.favorites.FavoriteSongsViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_favorite_music.*
import kotlinx.android.synthetic.main.fragment_favorite_music.view.*
import kotlinx.android.synthetic.main.fragment_music_catalog.view.*
import kotlinx.android.synthetic.main.fragment_music_catalog.view.app_logo
import kotlinx.android.synthetic.main.fragment_songs.view.*
import kotlinx.android.synthetic.main.row_layout_song.view.*

@AndroidEntryPoint
class SongsFragment : Fragment(), SongsAdapter.AddFavoriteSong,
    SongsAdapter.FillFavoriteSongsButton {

    private val arguments by navArgs<SongsFragmentArgs>()
    private lateinit var mView: View
    private val songsViewModel: SongsViewModel by activityViewModels()
    private val favoriteSongsViewModel: FavoriteSongsViewModel by activityViewModels()
    private val mAdapter: SongsAdapter by lazy { SongsAdapter(emptyList(), this, this) }
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
        mView.recycler_view_songs.layoutManager = StaggeredGridLayoutManager(
            columnCount,
            StaggeredGridLayoutManager.VERTICAL
        )
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

    override fun onClick(song: Song) {
        favoriteSongsViewModel.insert(song)
    }

    override fun fillButton(button: View) {
        val drawable = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_fill_fav) }
        button.icon_fav_empty.setImageDrawable(drawable)
    }

    override fun fillButtonIfFavorite(currentSong: Song, button: View) {
        if(favoriteSongsViewModel.allFavoritesSongs.value?.contains(currentSong) == true){
            fillButton(button)
        }
    }
}

