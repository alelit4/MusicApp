package com.alexandra.musicapp.ui.albums

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandra.musicapp.data.Connectivity.Companion.hasInternetConnection
import com.alexandra.musicapp.domain.models.Album
import com.alexandra.musicapp.domain.repositories.AlbumsRepository
import com.alexandra.musicapp.domain.utils.NetworkResult
import kotlinx.coroutines.launch

class AlbumsViewModel @ViewModelInject constructor (
    private val repository: AlbumsRepository,
    application: Application
): AndroidViewModel(application) {

    var albumsResponse: MutableLiveData<NetworkResult<List<Album>>> = MutableLiveData()

    fun searchAlbums(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchAlbumsSafeCall(searchQuery)
    }

    private suspend fun searchAlbumsSafeCall(queries: Map<String, String>) {
        albumsResponse.value = NetworkResult.Loading()
        if(hasInternetConnection(getApplication<Application>())){
            try {
                val response = repository.searchAlbums(queries)
                albumsResponse.value = handleArtistsResponse(response)
            }catch (e: Exception){
                albumsResponse.value = NetworkResult.Error("Album not found")
            }
        }
        else{
            albumsResponse.value = NetworkResult.Error("No Internet connection")
        }
    }

    private fun handleArtistsResponse(response: List<Album>): NetworkResult<List<Album>>? {
        return when {
            response.isNullOrEmpty() -> {
                NetworkResult.Error("Artist not found")
            }
            else -> {
                NetworkResult.Success(response)
            }
        }
    }


}