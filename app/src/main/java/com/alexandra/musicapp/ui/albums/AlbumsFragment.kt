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
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.R
import com.alexandra.musicapp.domain.models.Album
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
    private val albumsAdapter by lazy { AlbumsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_albums, container, false)

        setUpRecyclerView()
        setUpScrollListener()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val albumResponse = albumsViewModel.albumsResponse.value
        if (albumResponse?.data != null)
            albumsAdapter.setData(albumResponse.data)
        albumsViewModel.offset = 0
        requestAlbumsByArtistIdPaged(argArtist.artistId, queriesViewModel.blockSize)
    }

    private fun setUpRecyclerView() {
        mView.shimmer_albums.adapter = albumsAdapter
        mView.shimmer_albums.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpScrollListener() {
        val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && albumsViewModel.queryArtistId.isNotEmpty()) {
                    requestAlbumsByArtistIdPaged(albumsViewModel.queryArtistId, queriesViewModel.blockSize)
                }
            }
        }
        mView.shimmer_albums.addOnScrollListener(scrollListener)
    }

    private fun requestAlbumsByArtistIdPaged(artistId: String, blockSize: Int) {
        showShimmerEffect()
        albumsViewModel.searchAlbums(queriesViewModel.retrieveSearchArtistWorkQuery(artistId, blockSize, albumsViewModel.offset))
        albumsViewModel.albumsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> updateData(response, blockSize)
                is NetworkResult.Error -> showError(response)
                is NetworkResult.Loading -> showShimmerEffect()
            }
        })
    }

    private fun showError(response: NetworkResult<List<Album>>) {
        hideShimmerEffect()
        Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun updateData(response: NetworkResult<List<Album>>, blockSize: Int) {
        hideShimmerEffect()
        response.data?.let {
            when (albumsViewModel.offset) {
                0 -> albumsAdapter.setData(it)
                else -> albumsAdapter.addData(it)
            }
            this.albumsViewModel.offset += blockSize
        }
    }

    private fun showShimmerEffect() {
        if (albumsViewModel.offset == 0){
            mView.shimmer_albums.showShimmer()
        }
    }

    private fun hideShimmerEffect() {
        if (albumsViewModel.offset == 0) {
            mView.shimmer_albums.hideShimmer()
        }
    }

}