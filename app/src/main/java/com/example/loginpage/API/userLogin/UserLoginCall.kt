package com.example.loginpage.API.userLogin

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.RetornoApi
import com.example.loginpage.models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserLoginCall {
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")

    @POST("user/login")
    fun validate(@Body userLogin: UserLogin): Call<RetornoApi>
}