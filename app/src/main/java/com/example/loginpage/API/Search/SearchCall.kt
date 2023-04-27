package com.example.loginpage.API.Search

import com.example.loginpage.models.AnnouncementGet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchCall {

    /* * * * ANUNCIOS * * * */
    @GET("announcements/announcement-title/")
    fun searchAnnouncementsByName(@Query("announcementTitle") announcementTitle: String, @Query("userId") userID: Int): Call<List<AnnouncementGet>>
}