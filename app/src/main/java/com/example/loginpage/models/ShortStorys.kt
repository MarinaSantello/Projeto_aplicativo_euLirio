package com.example.loginpage.models

import com.google.gson.annotations.SerializedName


data class ShortStoryGet(
    var id:Int?,
    var titulo:String = "",
    var sinopse:String = "",
    var capa:String = "",
    var status:Int?,
    var historia:String = "",
    var data:String = "",
    var premium:Int?,
    var classificacao:List<Classificacao>,
    var usuario:List<Usuario>,
    var tipo:List<Tipo>,
    var generos:List<Generos>,
    var curtido: Boolean,
    var favorito: Boolean,
    var lido: Boolean,
    var avaliacao: Double,
    var comentado: Boolean,
    var comentarios: QTDcomentarios
){
    override fun toString(): String {
        return super.toString()
    }
}

data class QTDcomentarios (
    @SerializedName("quantidade_comentarios")
    var qtdComentarios: String
)