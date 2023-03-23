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
