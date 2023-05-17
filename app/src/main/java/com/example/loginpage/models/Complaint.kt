package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class ComplaintAnnoncement(
    var descricao: String,
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,
    var tipo: List<IdComplaintType>
)
data class ComplaintShortStory(
    var descricao: String,
    @SerializedName("id_historia_curta")
    var idAnuncio: Int?,
    var tipo: List<IdComplaintType>
)
data class ComplaintUser(
    var descricao: String,
    @SerializedName("id_usuario")
    var idAnuncio: Int?,
    var tipo: List<IdComplaintType>
)

data class ComplaintType(
    var id: String,
    var tipo: String
)

data class IdComplaintType(
    @SerializedName("id_tipo_denuncia")
    var id: Int
)