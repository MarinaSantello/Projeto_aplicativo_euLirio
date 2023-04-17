package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class FavoriteAnnouncement(
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class FavoriteShortStorie(
    @SerializedName("id_historia_curta")
    var idHistoriaCurta: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class CountFavoriteAnnouncement(
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class CountFavoriteShortStorie(
    @SerializedName("id_historia_curta")
    var idHistoriaCurta: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)