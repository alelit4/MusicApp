package com.alexandra.musicapp.ui.catalog

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandra.musicapp.data.Connectivity.Companion.hasInternetConnection
import com.alexandra.musicapp.domain.models.Artist
import com.alexandra.musicapp.domain.repositories.ArtistsRepository
import com.alexandra.musicapp.domain.utils.NetworkResult
import kotlinx.coroutines.launch

class CatalogViewModel @ViewModelInject constructor (
    private val repository: ArtistsRepository,
    application: Application
): AndroidViewModel(application) {

    var artistsResponse: MutableLiveData<NetworkResult<List<Artist>>> = MutableLiveData()

    fun searchArtists(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchArtistsSafeCall(searchQuery)
    }

    private suspend fun searchArtistsSafeCall(queries: Map<String, String>) {
        artistsResponse.value = NetworkResult.Loading()
        if(hasInternetConnection(getApplication<Application>())){
            try {
                val response = repository.searchArtists(queries)
                artistsResponse.value = handleArtistsResponse(response)
            }catch (e: Exception){
                artistsResponse.value = NetworkResult.Error("Problem finding artist")
            }
        }
        else{
            artistsResponse.value = NetworkResult.Error("No Internet connection")
        }
    }

    private fun handleArtistsResponse(response: List<Artist>): NetworkResult<List<Artist>> {
        return when {
            response.isNullOrEmpty() -> NetworkResult.Error("No data")
            else -> NetworkResult.Success(response)
        }
    }
}