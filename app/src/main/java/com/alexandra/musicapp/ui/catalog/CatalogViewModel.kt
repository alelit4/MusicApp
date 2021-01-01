package com.alexandra.musicapp.ui.catalog

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandra.musicapp.domain.CatalogRepository
import com.alexandra.musicapp.domain.models.Catalog
import com.alexandra.musicapp.domain.utils.NetworkResult
import com.alexandra.musicapp.utils.Constants.Companion.ALL_ARTIST
import com.alexandra.musicapp.utils.Constants.Companion.ALL_ARTIST_TERM
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ATTRIBUTE
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ENTITY
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_TERM
import kotlinx.coroutines.launch
import retrofit2.Response

class CatalogViewModel@ViewModelInject constructor (
    private val repository: CatalogRepository,
    application: Application
): AndroidViewModel(application) {

    var catalogResponse: MutableLiveData<NetworkResult<Catalog>> = MutableLiveData()

    fun searchArtists(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchArtistsSafeCall(searchQuery)
    }

    private suspend fun searchArtistsSafeCall(queries: Map<String, String>) {
        catalogResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.searchArtists(queries)
                catalogResponse.value = handleArtistsResponse(response)
            }catch (e: Exception){
                catalogResponse.value = NetworkResult.Error("Artist not found")
            }
        }
        else{
            catalogResponse.value = NetworkResult.Error("No Internet connection")
        }
    }

    private fun handleArtistsResponse(response: Response<Catalog>): NetworkResult<Catalog>? {
        when {
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Artist not found")
            }
            response.isSuccessful -> {
                val artists = response.body()
                return NetworkResult.Success(artists!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }

    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }

    }

    fun retrieveDemoQuery(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_TERM] = "oreja"
        queries[QUERY_ENTITY] = ALL_ARTIST
        queries[QUERY_ATTRIBUTE] = ALL_ARTIST_TERM
        return queries
    }

}