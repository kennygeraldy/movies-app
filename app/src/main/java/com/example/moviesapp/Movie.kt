package com.example.moviesapp

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val overview: String,
    val rating: Double
)