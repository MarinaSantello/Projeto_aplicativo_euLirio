package com.example.loginpage.API.Search

import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.ShortStoryGet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchCall {

    /* * * * ANUNCIOS * * * */
    @GET("announcements/announcement-title/")
    fun searchAnnouncementsByName(@Query("announcementTitle") announcementTitle: String, @Query("userId") userID: Int): Call<List<AnnouncementGet>>
    @GET("announcements/genre-name/")
    fun searchAnnouncementsByGenre(@Query("genreName") genreName: String, @Query("userId") userID: Int): Call<List<AnnouncementGet>>


    /* * * * PEQUENAS HISTÓRIAS * * * */
    @GET("/short-stories/storie-title/")
    fun searchShortStoriesByName(@Query("shortStorieTitle") shortStorieTitle: String, @Query("userId") userID: Int): Call<List<ShortStoryGet>>
    @GET("/short-stories/genre-name/")
    fun searchShortStoriesByGenre(@Query("genreName") genreName: String, @Query("userId") userID: Int): Call<List<ShortStoryGet>>

}