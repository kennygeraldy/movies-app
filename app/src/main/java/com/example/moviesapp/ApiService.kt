package com.example.moviesapp

import com.example.moviesapp.ui.Data.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

interface ApiService {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") apiKey: String): Call<MovieResponse>
}