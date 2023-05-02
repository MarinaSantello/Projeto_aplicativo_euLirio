package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Buy(
    @SerializedName("id_anuncio")
    var anuncioID: Int,

    @SerializedName("id_usuario")
    var userID: Int
)
