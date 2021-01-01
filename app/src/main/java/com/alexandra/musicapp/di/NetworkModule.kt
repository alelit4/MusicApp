package com.alexandra.musicapp.di

import com.alexandra.musicapp.data.api.CatalogApiService
import com.alexandra.musicapp.data.repository.CatalogApiRepository
import com.alexandra.musicapp.domain.CatalogRepository
import com.alexandra.musicapp.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

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
    fun provideCatalogApiService(retrofit: Retrofit): CatalogApiService {
        return retrofit.create(CatalogApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCatalogRepository(catalogApiService: CatalogApiService): CatalogRepository {
        return CatalogApiRepository(catalogApiService)
    }
}