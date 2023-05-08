package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Commit(
    var id: Int?,
    var titulo: String,
    var resenha: String,
    var avaliacao: Int,
    var spoiler: Boolean,

    @SerializedName("id_resposta")
    var idResposta: Int? = null,
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("id_anuncio")
    var announcementID: Int
)
