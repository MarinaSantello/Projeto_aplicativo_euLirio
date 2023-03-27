package com.example.loginpage.API.user

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.User
import com.example.loginpage.models.UserUpdate
import retrofit2.Call
import retrofit2.http.*

const val contentType = Constant.CONTENT_TYPE

interface UserCall {

    @GET("")
    fun getAll(): Call<List<User>>
    @GET("user/id/{id}")
    fun getByID(@Path("id") id: Long): Call<User>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("user")
    fun save(@Body user: User): Call<String>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @PUT("user/id/{id}")
    fun update(@Path("id") id: Int,
               @Body user: UserUpdate): Call<String>

    @DELETE("user/id/{id}")
    fun delete(@Path("id") id: Long): Call<String>
}