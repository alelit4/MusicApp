package com.alexandra.musicapp.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.R
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.utils.QueriesHelper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_albums.view.*

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private val arguments by navArgs<AlbumsFragmentArgs>()
    private val albumsViewModel: AlbumsViewModel by activityViewModels()
    private lateinit var mView: View
    private val albumsAdapter by lazy { AlbumsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_albums, container, false)
        setUpRecyclerView()
        setUpScrollHandler()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val albumResponse = albumsViewModel.albumsResponse.value
        if (albumResponse?.data != null)
            albumsAdapter.setData(albumResponse.data)
        albumsViewModel.offset = 0
        requestAlbumsByArtistIdPaged(arguments.artistId)
    }

    private fun setUpRecyclerView() {
        mView.shimmer_albums.adapter = albumsAdapter
        mView.shimmer_albums.layoutManager = LinearLayoutManager(requireContext())
    }

    private val scrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (couldLoadData(recyclerView)) {
                    requestAlbumsByArtistIdPaged(albumsViewModel.queryArtistId)
                }
            }
        }

    private fun setUpScrollHandler() {
        mView.shimmer_albums.addOnScrollListener(scrollListener)
        albumsViewModel.albumsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> updateView(response)
                is NetworkResult.Error -> showError(response.message.toString())
                is NetworkResult.Loading -> showShimmerEffect()
            }
        })
    }

    private fun requestAlbumsByArtistIdPaged(artistId: String) {
        showShimmerEffect()
        val searchQuery =
            QueriesHelper.retrieveSearchArtistWorkQuery(artistId, albumsViewModel.offset)
        albumsViewModel.searchAlbums(searchQuery)

    }

    private fun couldLoadData(recyclerView: RecyclerView): Boolean {
        return (!recyclerView.canScrollVertically(1)
                && albumsViewModel.queryArtistId.isNotEmpty()
                && !albumsViewModel.isLoading
                && !albumsViewModel.isAllDataDownloaded)
    }


    private fun showError(message: String) {
        hideShimmerEffect()
        albumsViewModel.isLoading = false
        Snackbar.make(mView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun updateView(response: NetworkResult<List<Album>>) {
        hideShimmerEffect()
        response.data?.let { data ->
            if (data.isNotEmpty()) updateAlbums(data)
        }
        albumsViewModel.isLoading = false
    }

    private fun updateAlbums(data: List<Album>) {
        when (albumsViewModel.offset) {
            0 -> albumsAdapter.setData(data)
            else -> albumsAdapter.addData(data)
        }
        this.albumsViewModel.isAllDataDownloaded = isAllDataDownloaded(data)
        this.albumsViewModel.offset += data.size
    }

    private fun isAllDataDownloaded(data: List<Album>) =
        (data.size < QueriesHelper.blockSize)

    private fun showShimmerEffect() {
        if (albumsViewModel.offset == 0) {
            mView.shimmer_albums.showShimmer()
        }
    }

    private fun hideShimmerEffect() {
        if (albumsViewModel.offset == 0) {
            mView.shimmer_albums.hideShimmer()
        }
    }

}