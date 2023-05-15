package com.example.loginpage.models

import com.example.loginpage.SQLite.model.UserID
import com.google.gson.annotations.SerializedName

data class Recommendation(
    var conteudo: String,
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("id_anuncio")
    var anuncioID: Int,
    var spoiler: String
)
