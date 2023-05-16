package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class LikeAnnouncement(
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,

    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class LikeShortStorie(
    @SerializedName("id_historia_curta")
    var idHistoriaCurta: Int?,

    @SerializedName("id_usuario")
    var idUsuario: Int?
)

data class CountAnnouncementLikes (
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,

    @SerializedName("quantidade_curtidas")
    var qtdeCurtidas: String
)

data class CountShortStorieLikes (
    @SerializedName("id_historia_curta")
    var idHistoriaCurta: Int?,

    @SerializedName("quantidade_curtidas")
    var qtdeCurtidas: String
)


data class likeRecommendation(
    @SerializedName("id_usuario")
    var idUsuario: Int?,
    @SerializedName("id_recomendacao")
    var idRecomendacao: Int?
)