package com.alexandra.musicapp.ui.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandra.musicapp.R
import com.alexandra.musicapp.domain.models.Catalog
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_music_catalog.view.*

@AndroidEntryPoint
class MusicCatalogFragment : Fragment() {

    private lateinit var mView: View
    private val catalogAdapter by lazy { CatalogAdapter() }
    private lateinit var catalogViewModel: CatalogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView =  inflater.inflate(R.layout.fragment_music_catalog, container, false)
        setUpRecyclerView()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catalogViewModel = (activity as MainActivity).catalogViewModel
        val artistResponse = catalogViewModel.catalogResponse.value
        if (artistResponse != null)
            catalogAdapter.setData(artistResponse.data as Catalog)
        // TODO delete it mocked data
      requestApiData()
    }

    private fun setUpRecyclerView() {
        mView.shimmer_catalog.adapter = catalogAdapter
        mView.shimmer_catalog.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestApiData() {
        showShimmerEffect()
        catalogViewModel.searchArtists(catalogViewModel.retrieveDemoQuery())
        catalogViewModel.catalogResponse.observe(viewLifecycleOwner, { response ->
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