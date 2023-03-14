package com.example.loginpage.API.genre

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Genre
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


const val contentType = Constant.CONTENT_TYPE

interface GenreCall {
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @GET("genres")
    fun getAll(): Call<List<Genre>>
}