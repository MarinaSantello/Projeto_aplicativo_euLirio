package com.example.loginpage.API.user

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.User
import retrofit2.Call
import retrofit2.http.*

const val contentType = Constant.CONTENT_TYPE

interface UserCall {
    @Headers("Content-Type:$contentType")

    @GET("")
    fun getAll(): Call<List<User>>

    @POST("user")
    fun save(@Body user: User): Call<String>

    @DELETE("/{id}")
    fun delete(@Path("id") id: Long): Call<String>
}