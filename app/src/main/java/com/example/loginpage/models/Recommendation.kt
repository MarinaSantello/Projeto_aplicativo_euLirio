package com.example.loginpage.models

import com.example.loginpage.SQLite.model.UserID
import com.google.gson.annotations.SerializedName

data class Recommendation(
    var id: Int?,
    var conteudo: String,
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("id_anuncio")
    var anuncioID: Int,
    var spoiler: String,
    @SerializedName("data_hora")
    var dataHora: String? = null,
    val curtidas: qtdeCurtidas? = null,
    var favoritos: qtdeFavoritos? = null,
    var curtido: Boolean? = null,
    var favorito: Boolean? = null,
    var usuario: List<UserRecommendation>?
)

data class UserRecommendation (
    @SerializedName("user_name")
    var userName: String,
    var nome: String,
    var foto: String
)


data class qtdeFavoritos(
    @SerializedName("quantidade_favoritos")
    var qtdeFavoritos: Int?
)