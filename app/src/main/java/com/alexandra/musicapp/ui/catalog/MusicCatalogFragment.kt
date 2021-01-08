package com.alexandra.musicapp.ui.catalog

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.R
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.utils.QueriesHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_music_catalog.view.*

@AndroidEntryPoint
class MusicCatalogFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mView: View
    private val catalogAdapter by lazy { CatalogAdapter() }
    private val catalogViewModel: CatalogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_music_catalog, container, false)
        setHasOptionsMenu(true)
        setUpRecyclerView()
        setUpScrollHandler()
        setUpAppLogoViewHandler()
        return mView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_music_catalog, menu)
        val search = menu.findItem(R.id.menu_search_artist)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(artistName: String?): Boolean {
        if (artistName != null && !catalogViewModel.isLoading) {
            catalogViewModel.queryArtistName = artistName
            catalogViewModel.offset = 0
            catalogViewModel.isAllDataDownloaded = false
            requestArtistsByNamePaged(artistName)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun setUpRecyclerView() {
        mView.shimmer_catalog.adapter = catalogAdapter
        mView.shimmer_catalog.layoutManager = LinearLayoutManager(requireContext())
    }

    private val scrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (couldLoadData(recyclerView)) {
                    requestArtistsByNamePaged(
                        catalogViewModel.queryArtistName,
                    )
                }
            }
        }

    private fun setUpScrollHandler() {
        mView.shimmer_catalog.addOnScrollListener(scrollListener)
        catalogViewModel.artistsResponse.observe(viewLifecycleOwner,
            { response ->
                when (response) {
                    is NetworkResult.Success -> updateView(response)
                    is NetworkResult.Error -> showError(response.message.toString())
                    is NetworkResult.Loading -> showShimmerEffect()
                }
            })

    }

    private fun setUpAppLogoViewHandler() {
        catalogAdapter.catalogSize.observe(viewLifecycleOwner,
            { artistsSize ->
                showAppLogo(artistsSize == 0)
            })
    }

    private fun couldLoadData(recyclerView: RecyclerView): Boolean {
        val could = !recyclerView.canScrollVertically(1)
                && catalogViewModel.queryArtistName.isNotEmpty()
                && !catalogViewModel.isLoading
                && !catalogViewModel.isAllDataDownloaded
        return could
    }

    private fun requestArtistsByNamePaged(artistName: String) {
        showShimmerEffect()
        catalogViewModel.isLoading = true
        val searchQuery = QueriesHelper.retrieveSearchArtistsQuery(
            artistName, catalogViewModel.offset
        )
        catalogViewModel.searchArtists(searchQuery)
    }

    private fun showError(message: String) {
        hideShimmerEffect()
        showAppLogo(true)
        catalogViewModel.isLoading = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateView(response: NetworkResult<List<Artist>>) {
        hideShimmerEffect()
        response.data?.let { data ->
            if (data.isNotEmpty()) updateArtists(data)
        }
        catalogViewModel.isLoading = false
    }

    private fun updateArtists(data: List<Artist>) {
        when (catalogViewModel.offset) {
            0 -> catalogAdapter.setData(data)
            else -> catalogAdapter.addData(data)
        }
        this.catalogViewModel.isAllDataDownloaded = isAllDataDownloaded(data)
        this.catalogViewModel.offset += data.size
    }

    private fun isAllDataDownloaded(data: List<Artist>) =
        (data.size < QueriesHelper.blockSize)

    private fun showShimmerEffect() {
        if (catalogViewModel.offset == 0) {
            showAppLogo(false)
            mView.shimmer_catalog.showShimmer()
        }
    }

    private fun hideShimmerEffect() {
        if (catalogViewModel.offset == 0) {
            mView.shimmer_catalog.hideShimmer()
        }
    }

    private fun showAppLogo(visible: Boolean) {
        when {
            visible -> mView.app_logo.visibility = View.VISIBLE
            else -> mView.app_logo.visibility = View.GONE
        }
    }
}