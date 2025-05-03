package com.capgemini.androidpoc.di

import com.capgemini.androidpoc.data.remote.api.CountryApi
import com.capgemini.androidpoc.data.repository.CountryRepositoryImpl
import com.capgemini.androidpoc.domain.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs headers + body
        }
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideCountryApi(okHttpClient: OkHttpClient): CountryApi {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // Add client here
            .build()
            .create(CountryApi::class.java)
    }

    @Provides
    fun provideCountryRepository(api: CountryApi): CountryRepository {
        return CountryRepositoryImpl(api)
    }
}
