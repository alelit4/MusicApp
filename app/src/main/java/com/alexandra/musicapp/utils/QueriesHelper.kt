package com.alexandra.musicapp.utils

class QueriesHelper {

    companion object Factory {

        const val blockSize: Int = 20

        private const val QUERY_TERM = "term"
        private const val QUERY_ENTITY = "entity"
        private const val QUERY_ATTRIBUTE = "attribute"
        private const val QUERY_ID = "id"
        private const val LIMIT = "limit"
        private const val OFFSET = "offset"
        private const val ALL_ARTIST = "allArtist"
        private const val ALL_ARTIST_TERM = "allArtistTerm"
        private const val ALBUM = "album"

        fun retrieveSearchArtistsQuery(artistName: String): HashMap<String, String> {
            val query: HashMap<String, String> = HashMap()
            query[QUERY_TERM] = artistName
            query[QUERY_ENTITY] = ALL_ARTIST
            query[QUERY_ATTRIBUTE] = ALL_ARTIST_TERM
            return query
        }

        fun retrieveSearchArtistsQuery(
            artistName: String, offset: Int
        ): HashMap<String, String> {
            val query: HashMap<String, String> = HashMap()
            query[QUERY_TERM] = artistName
            query[QUERY_ENTITY] = ALL_ARTIST
            query[QUERY_ATTRIBUTE] = ALL_ARTIST_TERM
            query["limit"] = blockSize.toString()
            query["offset"] = offset.toString()
            return query
        }

        fun retrieveSearchArtistWorkQuery(artistId: String): HashMap<String, String> {
            val query: HashMap<String, String> = HashMap()
            query[QUERY_ID] = artistId
            query[QUERY_ENTITY] = ALBUM
            return query
        }

        fun retrieveSearchArtistWorkQuery(
            artistId: String, offset: Int
        ): HashMap<String, String> {
            val query: HashMap<String, String> = HashMap()
            query[QUERY_ID] = artistId
            query[QUERY_ENTITY] = ALBUM
            query[LIMIT] = blockSize.toString()
            query[OFFSET] = offset.toString()
            return query
        }
    }
}