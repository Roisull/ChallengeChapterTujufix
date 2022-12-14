package com.chalchap7.challengechapter7.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitMovie {

    const val BASE_URL = "https://api.themoviedb.org/"
    const val BASE_URL_fav = "https://63403f57e44b83bc73ccaacc.mockapi.io/"

    private  val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    @Provides
    @Singleton
    fun provideRetrofit(): RestfulMovie =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RestfulMovie::class.java)

    @Provides
    @Singleton
    fun provideRetrofitFavorite(): RestfulApiFavorites =
        Retrofit.Builder()
            .baseUrl(BASE_URL_fav)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RestfulApiFavorites::class.java)

}