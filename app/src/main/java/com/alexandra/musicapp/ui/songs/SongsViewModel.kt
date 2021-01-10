package com.alexandra.musicapp.ui.songs

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.alexandra.musicapp.domain.models.Song
import com.alexandra.musicapp.domain.repositories.SongsRepository
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.ui.CustomDiffUtils
import com.alexandra.musicapp.utils.QueriesHelper
import kotlinx.coroutines.launch

class SongsViewModel @ViewModelInject constructor(
    private val repository: SongsRepository,
    application: Application
) : AndroidViewModel(application) {
    var offset: Int = 0
    var isLoading: Boolean = false
    var isAllDataDownloaded: Boolean = false
    var queryCollectionId: String = ""
    var songsResponse: MutableLiveData<NetworkResult<List<Song>>> = MutableLiveData()
    var songs: MutableLiveData<List<Song>> = MutableLiveData()

    fun searchSongs(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchSongsSafeCall(searchQuery)
    }

    private suspend fun searchSongsSafeCall(queries: Map<String, String>) {
        songsResponse.value = NetworkResult.Loading()
        try {
            val response = repository.searchSongs(queries)
            songsResponse.value = handleSongsResponse(response)
        } catch (e: Exception) {
            songsResponse.value = NetworkResult.Error("Song not found")
        }
    }

    private fun handleSongsResponse(response: List<Song>): NetworkResult<List<Song>> {
        return when {
            response.isNullOrEmpty() -> {
                NetworkResult.Error("No data available. Check your Internet Connection")
            }
            else -> {
                setData(response)
                NetworkResult.Success(response)
            }
        }
    }

    fun requestSongsByCollectionIdPaged(artistId: String) {
        val searchQuery = QueriesHelper.retrieveSearchSongsQuery(artistId, offset)
        searchSongs(searchQuery)
    }

    fun setData(songs: List<Song>) {
        val oldList = this.songs.value as MutableList<Song>
        val customDiffUtil = CustomDiffUtils(oldList.toList(), songs)
        val diffUtilResult = DiffUtil.calculateDiff(customDiffUtil)
        this.songs.value = songs
    }

    private fun addData(songs: List<Song>) {
        val oldList = this.songs.value as MutableList<Song>
        oldList.addAll(songs)
        this.songs.value = oldList
    }
}