package com.example.loginpage.API.genre

import com.example.loginpage.API.user.contentType
import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Genres
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

const val contentType = Constant.CONTENT_TYPE

interface GenreCall {
    @Headers("Content-Type:$contentType")

    @GET("genres")
    fun getAll(): Call<Genres>
}