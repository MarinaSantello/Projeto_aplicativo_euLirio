package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Announcements (
    val announcements: List<AnnouncementGet>
)


data class AnnouncementPost (
    var titulo: String,
    var volume: Int = 1,
    var capa: String,
    var sinopse: String = "",
    @SerializedName("quantidade_paginas")
    var qunatidadePaginas: Int,
    var preco: Float,
    @SerializedName("id_classificacao")
    var idClassificacao: Int,
    @SerializedName("id_usuario")
    var idUsuario: Int,
    @SerializedName("id_tipo_publicacao")
    var idTipoPublicacao: Int = 1,
    var epub: String,
    var pdf: String,
    var mobi: String?,
    val generos: List<Genero>
){
    override fun toString(): String {
        return super.toString()
    }
}

data class AnnouncementGet(
    var capa: String = "",
    var classificacao: List<Classificacao>,
    var data: String = "",
    var epub: String = "",
    val generos: List<Generos>,
    var id: Int?,
    var mobi: String = "",
    var pdf: String = "",
    var preco: Float?,
    var premium: Int?,
    @SerializedName("quantidade_paginas")
    var qunatidadePaginas: Int?,
    var sinopse: String = "",
    var status: Int?,
    var tipo: List<Tipo>,
    var titulo: String = "",
    var usuario: List<Usuario>,
    var volume: Int?,
    var curtido: Boolean,
    var favorito: Boolean,
    var lido: Boolean,
    var carrinho: Boolean,
    var compras: CountAnnouncementSales?,
    var comprado: Boolean,
    var avaliacao: Double,
    var comentado: Boolean,
    var comentarios: QTDcomentarios
){
    override fun toString(): String {
        return super.toString()
    }
}


data class Classificacao(
    var classificacao: String = "",
    var descricao: String = "",
    @SerializedName("id_classificacao")
    var idClassificacao: Int?
)

data class Generos(
    @SerializedName("id_genero")
    var idGenero: Int?,
    var nome: String = ""
)

data class Tipo(
    @SerializedName("id_tipo_publicacao")
    var idTipoPublicacao: Int
)

data class Usuario(
    var foto: String = "",
    @SerializedName("id_usuario")
    var idUsuario: Int?,
    @SerializedName("nome_usuario")
    var nomeUsuario: String = "",
    @SerializedName("user_name")
    var userName: String = ""
)

data class CountAnnouncementSales (
    @SerializedName("id_anuncio")
    var idAnuncio: Int?,

    @SerializedName("quantidade_compras")
    var qtdeCompras: String
)