package com.example.loginpage.models

data class Genres(
    val generos: List<GenreSearch>
)

data class GenreSearch (
    var id: Int
)

data class Genre(
    val id: Int,
    val nome: String
)