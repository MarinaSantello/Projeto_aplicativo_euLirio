package com.example.euLirio.API.user

import com.example.euLirio.constants.Constant
import com.example.euLirio.models.User
import retrofit2.Call
import retrofit2.http.*

const val contentType = Constant.CONTENT_TYPE

interface UserCall {
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")

    @GET("")
    fun getAll(): Call<List<User>>

    @POST("user")
    fun save(@Body user: User): Call<String>

    @DELETE("/{id}")
    fun delete(@Path("id") id: Long): Call<String>
}