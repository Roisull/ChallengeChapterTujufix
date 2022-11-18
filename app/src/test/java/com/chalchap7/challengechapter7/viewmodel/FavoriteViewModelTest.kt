package com.chalchap7.challengechapter7.viewmodel

import com.chalchap7.challengechapter7.model.FavoritesResponseItem
import com.chalchap7.challengechapter7.model.ResponMovieItem
import com.chalchap7.challengechapter7.network.RestfulApiFavorites
import com.chalchap7.challengechapter7.network.RestfulMovie
import io.mockk.every
import io.mockk.verify
import io.mockk.mockk
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class FavoriteViewModelTest{

    lateinit var serviceMovie: RestfulMovie
    lateinit var serviceFavorite: RestfulApiFavorites

    @Before
    fun setUp(){
        serviceMovie = mockk()
        serviceFavorite = mockk()
    }


    @Test
    fun getMovieTest():Unit = runBlocking{

        val respAllMovie  = mockk<Call<ResponMovieItem>>()

        every {
            runBlocking {
                serviceMovie.getAllMovie()
            }
        }returns respAllMovie

        val result = serviceMovie.getAllMovie()

        verify {
            runBlocking { serviceMovie.getAllMovie() }
        }
        assertEquals(result, respAllMovie)
    }


    @Test
    fun getFavoritesTest(): Unit = runBlocking {

        val respAllFav  = mockk<Call<List<FavoritesResponseItem>>>()

        every {
            runBlocking {
                serviceFavorite.getAllMovie()
            }
        }returns respAllFav


        val result = serviceFavorite.getAllMovie()

        verify {
            runBlocking { serviceFavorite.getAllMovie() }
        }
        assertEquals(result, respAllFav)

    }

    @Test
    fun addFavoritesTest(): Unit = runBlocking {
        val respAddFav = mockk<Call<FavoritesResponseItem>>()

        every {
            runBlocking {
                serviceFavorite.addNewMovie(FavoritesResponseItem("","lang","title","poster","date","vote","overview"))
            }
        } returns respAddFav

        val result = serviceFavorite.addNewMovie(FavoritesResponseItem("","lang","title","poster","date","vote","overview"))

        verify {
            runBlocking { serviceFavorite.addNewMovie(FavoritesResponseItem("","lang","title","poster","date","vote","overview"))
            }
        }
        assertEquals(result, respAddFav)

    }
    @Test
    fun deleteFavoritesTest(): Unit = runBlocking {
        val respDelFav = mockk<Call<Int>>()

        every {
            runBlocking {
                serviceFavorite.deleteMovie(1)
            }
        } returns respDelFav

        val result = serviceFavorite.deleteMovie(1)

        verify {
            runBlocking { serviceFavorite.deleteMovie(1) }
        }
        assertEquals(result, respDelFav)

    }
}


