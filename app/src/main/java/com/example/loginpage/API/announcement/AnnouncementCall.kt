package com.example.loginpage.API.announcement

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.AnnouncementPost
import retrofit2.Call
import retrofit2.http.*

interface AnnouncementCall {
    @GET("announcement/id/{id}")
    fun getByID(@Path("id") id: Int): Call<AnnouncementGet>

    @GET("announcements")
    fun getAllAnnouncements(): Call<List<AnnouncementGet>>

    @GET("announcements/user-id/{id}")
    fun getAllAnnouncementsByGenresUser(@Path("id") userID: Int): Call<List<AnnouncementGet>>
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("announcement")
    fun postAnnouncement(@Body announcementPost: AnnouncementPost): Call<String>
}