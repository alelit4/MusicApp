package com.alexandra.musicapp.di

import android.content.Context
import com.alexandra.musicapp.data.Connectivity.Companion.hasInternetConnection
import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.data.db.MusicAppDatabase
import com.alexandra.musicapp.data.repository.FavoriteSongsDatabaseRepository
import com.alexandra.musicapp.data.repository.MusicApiRepository
import com.alexandra.musicapp.data.db.SongsDao
import com.alexandra.musicapp.domain.repositories.AlbumsRepository
import com.alexandra.musicapp.domain.repositories.ArtistsRepository
import com.alexandra.musicapp.domain.repositories.SongsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    companion object {
        const val BASE_URL = "https://itunes.apple.com"
    }

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext applicationContext: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(applicationContext.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideHttpClient(@ApplicationContext applicationContext: Context, myCache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasInternetConnection(applicationContext))
                    request.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request
                        .newBuilder()
                        .header("Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                        .build()
                chain.proceed(request)
            }
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitIntance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideMusicApiService(retrofit: Retrofit): MusicApiService {
        return retrofit.create(MusicApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCatalogRepository(musicApiService: MusicApiService): ArtistsRepository {
        return MusicApiRepository(musicApiService)
    }

    @Singleton
    @Provides
    fun provideAlbumsRepository(musicApiService: MusicApiService): AlbumsRepository {
        return MusicApiRepository(musicApiService)
    }

    @Singleton
    @Provides
    fun provideSongsRepository(musicApiService: MusicApiService): SongsRepository {
        return MusicApiRepository(musicApiService)
    }

    @Singleton
    @Provides
    fun provideMusicAppDatabase(@ApplicationContext application: Context): MusicAppDatabase {
        return MusicAppDatabase.getDatabase(application)
    }


    @Singleton
    @Provides
    fun provideSongsDao(database: MusicAppDatabase): SongsDao {
        return database.songsDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteSongsRepository(songsDao: SongsDao): FavoriteSongsDatabaseRepository {
        return FavoriteSongsDatabaseRepository(songsDao)
    }


}