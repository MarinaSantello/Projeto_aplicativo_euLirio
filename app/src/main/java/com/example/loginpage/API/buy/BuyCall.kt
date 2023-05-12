package com.example.loginpage.API.buy

import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Constant
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.Buy
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface BuyCall {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("buy-announcement")
    fun buyAnnouncement(@Body buy: Buy): Call<String>

    @GET("purchased-announcements/user-id/{id}")
    fun getPurchasedAnnouncements(@Path("id") userID: Int): Call<List<AnnouncementGet>>
}