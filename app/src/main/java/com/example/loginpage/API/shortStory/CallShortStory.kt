package com.example.loginpage.API.shortStory

import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.ShortStoryGet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CallShortStory {

    @GET("short-storie/id/{id}")
    fun getByID(@Path("id") id: Int): Call<ShortStoryGet>

    @GET("short-stories")
    fun getAllShortStories(): Call<List<ShortStoryGet>>
}