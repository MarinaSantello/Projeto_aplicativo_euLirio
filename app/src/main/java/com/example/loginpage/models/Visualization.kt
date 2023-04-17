package com.example.loginpage.models

import com.google.gson.annotations.SerializedName


data class VisualizationAnnouncement(
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class CountVisualizationAnnouncement(
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class VisualizationShortStorie(
    @SerializedName("id_historia_curta")
    var idHistoriaCurta: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class CountVisualizationShortStorie(
    @SerializedName("id_historia_curta")
    var idHistoriaCurta: Int?,
    @SerializedName("id_usuario")
    var idUsuario: Int?
)