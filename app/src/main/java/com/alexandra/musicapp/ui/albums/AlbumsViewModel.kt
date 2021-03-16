package com.alexandra.musicapp.ui.albums

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.domain.repositories.AlbumsRepository
import com.alexandra.musicapp.domain.utils.NetworkResult
import kotlinx.coroutines.launch

class AlbumsViewModel @ViewModelInject constructor(
    private val repository: AlbumsRepository,
    application: Application
) : AndroidViewModel(application) {
    var offset: Int = 0
    var isLoading: Boolean = false
    var isAllDataDownloaded: Boolean = false
    var queryArtistId: String = ""
    var albumsResponse: MutableLiveData<NetworkResult<List<Album>>> = MutableLiveData()

    fun searchAlbums(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchAlbumsSafeCall(searchQuery)
    }

    private suspend fun searchAlbumsSafeCall(queries: Map<String, String>) {
        albumsResponse.value = NetworkResult.Loading()
        try {
            val response = repository.searchAlbums(queries)
            albumsResponse.value = handleArtistsResponse(response)
        } catch (e: Exception) {
            albumsResponse.value = NetworkResult.Error("Album not found")
        }
    }

    private fun handleArtistsResponse(response: List<Album>): NetworkResult<List<Album>> {
        return when {
            response.isNullOrEmpty() -> NetworkResult.Error("No data available. Check your Internet Connection")
            else -> NetworkResult.Success(response)
        }
    }
}