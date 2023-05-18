package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("nome_genero")
    var nomesGen: List<GenreSearch>
)

data class GenreSearch (
    var nome: String
)

data class Genre(
    val id: Int,
    val nome: String
)