package com.example.loginpage.models

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class Buy(
    @SerializedName("id_anuncio")
    var anuncioID: Int
){
    override fun toString(): String {
        return super.toString()
    }
}

data class BuyConfirm(
    @SerializedName("id_anuncio")
    var anuncioID: Int,

    @SerializedName("id_usuario")
    var userID: Int,

    @SerializedName("id_stripe")
    var stripeID: String
)

data class ConfirmBuyCarrinho(
    var data: ObjectBuy
)

data class ObjectBuy(
    @SerializedName("object")
    var objeto: IdBuyStripe
)

data class IdBuyStripe(
    var id: String
)

data class StripeConfirmed(
    var received: Boolean
)


