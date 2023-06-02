package com.example.loginpage.API.stripe

import android.telecom.Call
import com.example.loginpage.constants.Constant
import com.example.loginpage.models.BuyAnnouncement
import com.example.loginpage.models.UrlStripe
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface CallStripe {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("intent-buy/user-id/{id}")
    fun callUrlStripe(@Path("id") userID: Int, @Body AnnouncementId: BuyAnnouncement): retrofit2.Call<UrlStripe>


}