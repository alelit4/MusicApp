package com.alexandra.musicapp.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandra.musicapp.R
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.ui.QueriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_albums.view.*

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private val argArtist by navArgs<AlbumsFragmentArgs>()
    private val albumsViewModel: AlbumsViewModel by activityViewModels()
    private val queriesViewModel: QueriesViewModel by viewModels()
    private lateinit var mView: View
    private val mAdapter by lazy { AlbumsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_albums, container, false)

        setUpRecyclerView()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val albumResponse = albumsViewModel.albumsResponse.value
        if (albumResponse?.data != null)
            mAdapter.setData(albumResponse.data)
        requestApiData(argArtist.artistId)
    }

    private fun requestApiData(artistId: String) {
        showShimmerEffect()
        albumsViewModel.searchAlbums(queriesViewModel.retrieveSearchArtistWorkQuery(artistId))
        albumsViewModel.albumsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        mView.shimmer_albums.adapter = mAdapter
        mView.shimmer_albums.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showShimmerEffect() {
        mView.shimmer_albums.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.shimmer_albums.hideShimmer()
    }

}