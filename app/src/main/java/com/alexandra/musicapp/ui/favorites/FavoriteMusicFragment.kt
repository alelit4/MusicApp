package com.alexandra.musicapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alexandra.musicapp.R
import com.alexandra.musicapp.data.mediaplayer.MediaPlayer
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.ui.songs.SongsAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_favorite_music.*
import kotlinx.android.synthetic.main.fragment_favorite_music.view.*
import kotlinx.android.synthetic.main.row_layout_song.view.*

@AndroidEntryPoint
class FavoriteMusicFragment : Fragment(), SongsAdapter.AddFavoriteSong,
    SongsAdapter.FillFavoriteSongsButton {
    private val TAG = "FavoriteMusicFragment"
    private lateinit var mView: View
    private val favoriteSongsViewModel: FavoriteSongsViewModel by activityViewModels()
    private val mAdapter: SongsAdapter by lazy { SongsAdapter(emptyList(), this, this) }
    val columnCount = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_favorite_music, container, false)
        setUpRecyclerView()
        setUpAppLogoViewHandler()
        return mView
    }

    private fun setUpRecyclerView() {
        favoriteSongsViewModel.allFavoritesSongs.value?.let { mAdapter.setData(it) }
        mView.recycler_view_favorite_songs.adapter = mAdapter
        mView.recycler_view_favorite_songs.layoutManager = StaggeredGridLayoutManager(
            columnCount,
            StaggeredGridLayoutManager.VERTICAL
        )
        mView.recycler_view_favorite_songs.itemAnimator =
            SlideInUpAnimator().apply { addDuration = 300 }
        favoriteSongsViewModel.allFavoritesSongs.observe(viewLifecycleOwner, { songs ->
            songs?.let { mAdapter.setData(it) }
        })
    }

    override fun onClick(song: Song) {
        favoriteSongsViewModel.delete(song)
    }

    private fun setUpAppLogoViewHandler() {
        favoriteSongsViewModel.allFavoritesSongs.observe(viewLifecycleOwner,
            { songs ->
                showEmptyLogo(songs.isNullOrEmpty())
            })
    }

    private fun showEmptyLogo(visible: Boolean) {
        when {
            visible -> favorite_list_empty.visibility = View.VISIBLE
            else -> favorite_list_empty.visibility = View.GONE
        }
    }

    override fun fillButton(button: View) {
        val drawable = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_fill_fav) }
        button.icon_fav_empty.setImageDrawable(drawable)
    }

    override fun fillButtonIfFavorite(currentSong: Song, button: View) {
        fillButton(button)
    }

    override fun onPause() {
        super.onPause()
        MediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        MediaPlayer.stop()
    }
}