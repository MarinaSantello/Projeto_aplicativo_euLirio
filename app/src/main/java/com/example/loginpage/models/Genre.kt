package com.example.loginpage.models

data class Genres(
    val generos: List<Genre>
)

data class Genre(
    val id: Int,
    val nome: String
)