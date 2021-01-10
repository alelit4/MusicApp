package com.alexandra.musicapp.ui.favorites

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alexandra.musicapp.data.repository.FavoriteSongsRepository
import com.alexandra.musicapp.domain.models.Song
import kotlinx.coroutines.launch

class FavoriteSongsViewModel @ViewModelInject constructor (
    private val repository: FavoriteSongsRepository,
    application: Application
): AndroidViewModel(application)   {

    val allFavoritesSongs: LiveData<List<Song>> = repository.getAllFavoriteSongs()

    fun insert(song: Song) = viewModelScope.launch {
        repository.insert(song)
    }

    fun delete(song: Song) = viewModelScope.launch {
        repository.delete(song)
    }
}

