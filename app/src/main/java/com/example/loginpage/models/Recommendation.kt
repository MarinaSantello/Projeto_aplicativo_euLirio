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
    var dataHora: String,
    val curtidas: qtdeCurtidas,
    var favoritos: qtdeFavoritos,
    var curtido: Boolean?,
    var favorito: Boolean?
)


data class qtdeFavoritos(
    @SerializedName("quantidade_favoritos")
    var qtdeFavoritos: Int?
)