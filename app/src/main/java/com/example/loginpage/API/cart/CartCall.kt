package com.example.loginpage.API.cart

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.CartList
import com.example.loginpage.models.Cart
import retrofit2.Call
import retrofit2.http.*

interface CartCall {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("new-cart-item/user-id/{id}")
    fun putInCart(@Path("id") userID: Int, @Body cart: Cart): Call<String>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("confirm-buy/user-id/{id}")
    fun buyItemsCart(@Path("id") userID: Int, @Body cart: Cart): Call<String>

    @GET("list-cart-items/{id}")
    fun getItemsCart(@Path("id") userID: Int): Call<CartList>

//    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @DELETE("delete-cart-item/")
    fun deleteItem(@Query("announcementId") announcementID: Int, @Query("userId") userID: Int): Call<String>
}