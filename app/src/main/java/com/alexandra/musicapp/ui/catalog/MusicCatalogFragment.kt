package com.alexandra.musicapp.ui.catalog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexandra.musicapp.R
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.ui.QueriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_music_catalog.view.*

@AndroidEntryPoint
class MusicCatalogFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mView: View
    private val catalogAdapter by lazy { CatalogAdapter() }
    private val catalogViewModel: CatalogViewModel by activityViewModels()
    private val queriesViewModel: QueriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_music_catalog, container, false)
        setHasOptionsMenu(true)
        setUpRecyclerView()
        setUpScrollListener()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val artistResponse = catalogViewModel.artistsResponse.value
        if (artistResponse?.data != null)
            catalogAdapter.setData(artistResponse.data)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_music_catalog, menu)
        val search = menu.findItem(R.id.menu_search_artist)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(artistName: String?): Boolean {
        if (artistName != null) {
            catalogViewModel.queryArtistName = artistName
            catalogViewModel.offset = 0
            requestArtistsByNamePaged(artistName, queriesViewModel.blockSize)
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

    private fun setUpScrollListener() {
        val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (couldLoadData(recyclerView)) {
                    requestArtistsByNamePaged(catalogViewModel.queryArtistName, queriesViewModel.blockSize)
                }
            }
        }
        mView.shimmer_catalog.addOnScrollListener(scrollListener)
        catalogViewModel.artistsResponse.observe(viewLifecycleOwner,
            { response ->
                when (response) {
                    is NetworkResult.Success -> updateData(response, queriesViewModel.blockSize)
                    is NetworkResult.Error -> showError(response)
                    is NetworkResult.Loading -> showShimmerEffect()
                }
            })
    }

    private fun couldLoadData(recyclerView: RecyclerView): Boolean {
        return !recyclerView.canScrollVertically(1)
                && catalogViewModel.queryArtistName.isNotEmpty()
                && !catalogViewModel.isLoading
    }

    private fun requestArtistsByNamePaged(artistName: String, blockSize: Int) {
        showShimmerEffect()
        catalogViewModel.isLoading = true
        val searchQuery = queriesViewModel.retrieveSearchArtistsQuery(artistName, blockSize, catalogViewModel.offset)
        catalogViewModel.searchArtists(searchQuery)

    }

    private fun showError(response: NetworkResult<List<Artist>>) {
        hideShimmerEffect()
        catalogViewModel.isLoading = false
        Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun updateData(response: NetworkResult<List<Artist>>, blockSize: Int) {
        hideShimmerEffect()
        response.data?.let { data ->
            if (data.isNotEmpty()){
                when (catalogViewModel.offset) {
                    0 -> {catalogAdapter.setData(data)}
                    else ->{ catalogAdapter.addData(data)}
                }
                this.catalogViewModel.offset += data.size
            }
        }
        catalogViewModel.isLoading = false
    }

    private fun showShimmerEffect() {
        if (catalogViewModel.offset == 0){
            mView.shimmer_catalog.showShimmer()
        }
    }

    private fun hideShimmerEffect() {
        if (catalogViewModel.offset == 0) {
            mView.shimmer_catalog.hideShimmer()
        }
    }

    override fun onPause() {
        super.onPause()
    }
}