package com.example.loginpage.API.search

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.http.*

interface SearchCall {

    /* * * * ANUNCIOS * * * */
    @GET("announcements/announcement-title/")
    fun searchAnnouncementsByName(@Query("announcementTitle") announcementTitle: String, @Query("userId") userID: Int): Call<List<AnnouncementGet>>
    @GET("announcements/genre-name/")
    fun searchAnnouncementsByGenre(@Query("genreName") genreName: String, @Query("userId") userID: Int): Call<List<AnnouncementGet>>
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("filter-announcements/")
    fun filterAnnouncements(@Body genres: Genres, @Query("minValue") minValue: String, @Query("maxValue") maxValue: String, @Query("userId") userID: Int): Call<List<AnnouncementGet>>


    /* * * * PEQUENAS HISTÓRIAS * * * */
    @GET("/short-stories/storie-title/")
    fun searchShortStoriesByName(@Query("shortStorieTitle") shortStoryTitle: String, @Query("userId") userID: Int): Call<List<ShortStoryGet>>
    @GET("/short-stories/genre-name/")
    fun searchShortStoriesByGenre(@Query("genreName") genreName: String, @Query("userId") userID: Int): Call<List<ShortStoryGet>>
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("/short-stories-by-genres/user-id/{id}")
    fun searchShortStoriesByGenres(@Body genres: Genres, @Path("id") userID: Int): Call<List<ShortStoryGet>>

    /* * * * AUTORES * * * */
    @GET("/user/user-name/")
    fun authorSearch(@Query("username") author: String, @Query("userId") userID: Int): Call<List<UserFollow>>

}