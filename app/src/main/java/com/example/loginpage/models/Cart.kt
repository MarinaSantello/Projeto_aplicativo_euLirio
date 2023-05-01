package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Cart1(
    @SerializedName("id_anuncio")
    var anuncioID: Int,

    @SerializedName("id_usuario")
    var userID: Int
)

data class Cart (
    @SerializedName("id_anuncio")
    var idAnuncio: List<CartItems>
)

data class CartList (
    var items: List<CartData>,
    var total: Float
)

data class CartData (
    @SerializedName("id_anuncio")
    var anuncioID: Int,

    var titulo: String,
    var capa: String,
    var preco: Float,
    var pdf: String,
    var epub: String,
    var mobi: String,

    @SerializedName("id_carrinho")
    var carrinhoID: Int
)

data class CartItems (
    var id: Int
)