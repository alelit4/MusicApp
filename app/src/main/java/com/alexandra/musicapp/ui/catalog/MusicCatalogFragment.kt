package com.alexandra.musicapp.ui.catalog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandra.musicapp.R
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.ui.QueriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_music_catalog.view.*

@AndroidEntryPoint
class MusicCatalogFragment : Fragment(), SearchView.OnQueryTextListener  {

    private lateinit var mView: View
    private val catalogAdapter by lazy { CatalogAdapter() }
    private val catalogViewModel: CatalogViewModel by activityViewModels()
    private val queriesViewModel: QueriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView =  inflater.inflate(R.layout.fragment_music_catalog, container, false)
        setHasOptionsMenu(true)
        setUpRecyclerView()
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
        if (artistName != null)
            requestArtistsByName(artistName)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun setUpRecyclerView() {
        mView.shimmer_catalog.adapter = catalogAdapter
        mView.shimmer_catalog.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestArtistsByName(artistName: String) {
        showShimmerEffect()
        catalogViewModel.searchArtists(queriesViewModel.retrieveSearchArtistsQuery(artistName))
        catalogViewModel.artistsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { catalogAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun showShimmerEffect() {
        mView.shimmer_catalog.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.shimmer_catalog.hideShimmer()
    }
}