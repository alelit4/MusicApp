package com.alexandra.musicapp.di

import android.content.Context
import com.alexandra.musicapp.data.api.MusicApiService
import com.alexandra.musicapp.data.db.MusicAppDatabase
import com.alexandra.musicapp.data.repository.FavoriteSongsRepository
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
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
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
    fun provideRetrofitIntance(okHttpClient: OkHttpClient,
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
    fun provideFavoriteSongsRepository(songsDao: SongsDao): FavoriteSongsRepository {
        return FavoriteSongsRepository(songsDao)
    }



}