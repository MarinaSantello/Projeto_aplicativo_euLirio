package com.example.loginpage.API.shortStory

import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.ShortStoryGet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CallShortStory {

    @GET("short-storie/id/")
    fun getByID(@Query("shortStorieId") shortStoryID: Int, @Query("userId") userID: Int): Call<List<ShortStoryGet>>

    @GET("short-stories")
    fun getAllShortStories(): Call<List<ShortStoryGet>>

    @GET("short-stories/user-id/{id}")
    fun getAllShortStoriesByGenreUser(@Path("id") idUser: Int): Call<List<ShortStoryGet>>
}