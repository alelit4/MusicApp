package com.alexandra.musicapp.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.alexandra.musicapp.utils.Constants.Companion.ALBUM
import com.alexandra.musicapp.utils.Constants.Companion.ALL_ARTIST
import com.alexandra.musicapp.utils.Constants.Companion.ALL_ARTIST_TERM
import com.alexandra.musicapp.utils.Constants.Companion.LIMIT
import com.alexandra.musicapp.utils.Constants.Companion.OFFSET
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ATTRIBUTE
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ENTITY
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_ID
import com.alexandra.musicapp.utils.Constants.Companion.QUERY_TERM

class QueriesViewModel @ViewModelInject constructor(
    application: Application
): AndroidViewModel(application) {
    val blockSize: Int = 10

    fun retrieveSearchArtistsQuery(artistName: String): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_TERM] = artistName
        queries[QUERY_ENTITY] = ALL_ARTIST
        queries[QUERY_ATTRIBUTE] = ALL_ARTIST_TERM
        return queries
    }

    fun retrieveSearchArtistsQuery(artistName: String, blockSize: Int, offset: Int): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_TERM] = artistName
        queries[QUERY_ENTITY] = ALL_ARTIST
        queries[QUERY_ATTRIBUTE] = ALL_ARTIST_TERM
        queries["limit"] = blockSize.toString()
        queries["offset"] = offset.toString()
        return queries
    }

    fun retrieveSearchArtistWorkQuery(artistId: String): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_ID] = artistId
        queries[QUERY_ENTITY] = ALBUM
        return queries
    }

    fun retrieveSearchArtistWorkQuery(artistId: String, blockSize: Int, offset: Int): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_ID] = artistId
        queries[QUERY_ENTITY] = ALBUM
        queries[LIMIT] = blockSize.toString()
        queries[OFFSET] = offset.toString()
        return queries
    }
}