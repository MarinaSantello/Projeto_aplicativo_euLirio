package com.example.loginpage.API.announcement

import com.example.loginpage.models.AnnouncementGet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AnnouncementCall {
    @GET("announcement/id/{id}")
    fun getByID(@Path("id") id: Int): Call<AnnouncementGet>

    @GET("announcements")
    fun getAllAnnouncements(): Call<List<AnnouncementGet>>

    @GET("announcements/user-id/{id}")
    fun getAllAnnouncementsByGenresUser(@Path("id") userID: Int): Call<List<AnnouncementGet>>
}