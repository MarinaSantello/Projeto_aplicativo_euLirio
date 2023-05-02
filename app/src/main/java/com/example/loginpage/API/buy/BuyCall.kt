package com.example.loginpage.API.buy

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Buy
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BuyCall {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("buy-announcement")
    fun buyAnnouncement(@Body buy: Buy): Call<String>
}