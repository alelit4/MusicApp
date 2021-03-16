package com.alexandra.musicapp.ui.favorites

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alexandra.musicapp.data.repositories.FavoriteSongsDatabaseRepository
import com.alexandra.musicapp.domain.models.Song
import kotlinx.coroutines.launch

class FavoriteSongsViewModel @ViewModelInject constructor (
    private val databaseRepository: FavoriteSongsDatabaseRepository,
    application: Application
): AndroidViewModel(application)   {

    val allFavoritesSongs: LiveData<List<Song>> = databaseRepository.getAllFavoriteSongs()

    fun insert(song: Song) = viewModelScope.launch {
        databaseRepository.insert(song)
    }

    fun delete(song: Song) = viewModelScope.launch {
        databaseRepository.delete(song)
    }
}

