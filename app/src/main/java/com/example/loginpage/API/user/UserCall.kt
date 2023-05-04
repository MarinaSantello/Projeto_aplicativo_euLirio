package com.example.loginpage.API.user

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.User
import com.example.loginpage.models.UserName
import com.example.loginpage.models.UserUpdate
import retrofit2.Call
import retrofit2.http.*

interface UserCall {

    @GET("")
    fun getAll(): Call<List<User>>
    @GET("user/id/")
    fun getByID(@Query("searchUser") searchUser: Long, @Query("currentUser") currentUser: Long): Call<User>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("user")
    fun save(@Body user: User): Call<String>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @PUT("user/id/{id}")
    fun update(@Path("id") id: Int,
               @Body user: UserUpdate): Call<String>

    @DELETE("user/id/{id}")
    fun delete(@Path("id") id: Int): Call<String>
    @GET("verify-username/{username}")
    fun verifyUserName(@Path("username") username: String): Call<UserName>
}