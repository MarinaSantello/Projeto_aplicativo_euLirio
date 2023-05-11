package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Commit(
    var id: Int?,
    var titulo: String,
    var resenha: String,
    var avaliacao: Int,
    var spoiler: String,

    @SerializedName("id_resposta")
    var idResposta: Int? = null,
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("id_anuncio")
    var announcementID: Int,
    @SerializedName("data_publicado")
    var dataPublicado: String? = null,
    var curtido: Boolean? = null,
    var curtidas: qtdeCurtidas? = null
)

data class qtdeCurtidas(
    @SerializedName("quantidade_curtidas")
    var qtdeCurtidas: Int?
)

data class CommitSS(
    var id: Int?,
    var titulo: String,
    var resenha: String,
    var avaliacao: Int,
    var spoiler: String,

    @SerializedName("id_resposta")
    var idResposta: Int? = null,
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("id_historia_curta")
    var shortStoryID: Int,
    @SerializedName("data_publicacao")
    var dataPublicado: String? = null
)

data class LikeComment(
    @SerializedName("id_comentario")
    var idComentario: Int,
    @SerializedName("id_usuario")
    var idUsuario: Int
)
