package com.chalchap7.challengechapter7.network


import com.chalchap7.challengechapter7.model.ResponMovieItem
import retrofit2.Call
import retrofit2.http.GET

interface RestfulMovie {
    @GET("3/movie/5/recommendations?api_key=51d4336c22284544f84ccdd06444cf17")
    fun getAllMovie() : Call<ResponMovieItem>

}