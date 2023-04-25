package com.example.loginpage.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    @SerializedName("user_name") // indica para o gson qual a chave equivalente desse atributo
    var userName: String = "",
    @SerializedName("data_nascimento")
    var dataNascimento: String = "",

    var id: Int = 0,
    var nome: String = "",
    var foto: String = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
    var biografia: String = "",
    var email: String = "",
    var uid: String = "",

    var tags: List<Tag>,
    var generos: List<Genero>,
    @SerializedName("anuncios_desativados")
    var anunciosDeactivate: List<Anuncios>?,

    @SerializedName("anuncios_ativos")
    var anunciosActivate: List<Anuncios>?,

    @SerializedName("historias_curtas_ativas")
    var shortStoriesActivate: List<Anuncios>?,

    @SerializedName("historias_curtas_desativadas")
    var shortStoriesDeactivate: List<Anuncios>?
) {
    override fun toString(): String {
        return super.toString()
    }
}

data class UserUpdate(
    @SerializedName("user_name") // indica para o gson qual a chave equivalente desse atributo
    var userName: String = "",
    @SerializedName("id_tag_1")
    var tag01: Int?,
    @SerializedName("id_tag_2")
    var tag02: Int?,

    var nome: String = "",
    var foto: String = "",
    var biografia: String = "",
    var premium: Boolean = false,

    var generos: List<Genero>
) {
    override fun toString(): String {
        return super.toString()
    }
}

data class Tag (
    @SerializedName("id_tag")
    var idTag: Int = 0,
    @SerializedName("nome_tag")
    var nomeTag: String = ""
)

data class Genero (
    @SerializedName("id_genero")
    var idGenero: Int = 0,
    @SerializedName("nome_genero")
    var nomeGenero: String = ""
)

data class Anuncios (
    var id: Int,
    var titulo: String
)

data class UserName (
    var id: Int
)
