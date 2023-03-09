package com.example.loginpage.API.user

import com.example.loginpage.constants.Constant
import com.example.loginpage.model.user.User
import com.google.gson.internal.bind.TypeAdapters.UUID
import retrofit2.Call
import retrofit2.http.*

//const val contentType =

interface UserCall {

    @GET("")
    fun getAll(): Call<List<User>>

    @Headers("Content-Type: application/json")
    @POST("user")
    fun save(
        @Body user: User
    ): Call<String>

    @DELETE("/{id}")
    fun delete(@Path("id") id: Long): Call<String>
}