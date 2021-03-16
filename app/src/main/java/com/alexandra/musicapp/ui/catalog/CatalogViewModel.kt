package com.alexandra.musicapp.ui.catalog

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandra.musicapp.di.NetworkModule
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.repositories.ArtistsRepository
import com.alexandra.musicapp.domain.utils.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @ViewModelInject constructor(
    private val repository: ArtistsRepository,
    application: Application
) : AndroidViewModel(application) {
    var offset: Int = 0
    var isLoading: Boolean = false
    var isAllDataDownloaded: Boolean = false
    var queryArtistName: String = ""
    var artistsResponse: MutableLiveData<NetworkResult<List<Artist>>> = MutableLiveData()

    @Inject
    lateinit var network: NetworkModule

    fun searchArtists(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchArtistsSafeCall(searchQuery)
    }

    private suspend fun searchArtistsSafeCall(queries: Map<String, String>) {
        artistsResponse.value = NetworkResult.Loading()
        try {
            val response = repository.searchArtists(queries)
            artistsResponse.value = handleArtistsResponse(response)
        } catch (e: Exception) {
            artistsResponse.value = NetworkResult.Error("Problem finding artist")
        }
    }

    private fun handleArtistsResponse(response: List<Artist>): NetworkResult<List<Artist>> {
        return when {
            response.isNullOrEmpty() && isFirstSearch() -> NetworkResult.Error("No data available. Check your Internet Connection")
            else -> NetworkResult.Success(response)
        }
    }

    private fun isFirstSearch() = this.offset == 0
}