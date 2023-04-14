package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Announcements (
    val announcements: List<AnnouncementGet>
)

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
    var curtido: Boolean?
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
    var nome: String
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