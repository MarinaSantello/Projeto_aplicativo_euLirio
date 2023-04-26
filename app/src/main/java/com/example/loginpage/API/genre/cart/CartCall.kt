package com.example.loginpage.API.cart

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Cart
import com.example.loginpage.models.CartData
import retrofit2.Call
import retrofit2.http.*

interface CartCall {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("put-in-cart")
    fun putInCart(@Body cart: Cart): Call<String>

    @GET("list-cart-items/{id}")
    fun getItemsCart(@Path("id") userID: Int): Call<List<CartData>>
}