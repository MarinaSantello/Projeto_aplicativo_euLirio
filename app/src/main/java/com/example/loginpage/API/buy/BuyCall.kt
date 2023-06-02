package com.example.loginpage.API.buy

import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Constant
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface BuyCall {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("intent-buy-announcement/user-id/{id}")
    fun buyAnnouncement(@Path("id") userID: Int, @Body idAnuncio: Buy): Call<UrlStripe>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("intent-directly-payment-update")
    fun confirmBuyAnnouncement(@Body idAnuncio: BuyConfirm): Call<StripeConfirmed>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("intent-buy/user-id/{id}")
    fun buyAnnouncementsCarrinho(@Path("id") userId: Int, @Body listAnnouncements:BuyAnnouncement): Call<UrlStripe>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("intent-payment-update")
    fun confirmBuyAnnouncementsCarrinho(@Body idConfirm: ConfirmBuyCarrinho): Call<StripeConfirmed>


    @GET("purchased-announcements/user-id/{id}")
    fun getPurchasedAnnouncements(@Path("id") userID: Int): Call<List<AnnouncementGet>>
}