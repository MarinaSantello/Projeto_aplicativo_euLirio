package com.example.loginpage.API.follow

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Follow
import com.example.loginpage.models.User
import retrofit2.Call
import retrofit2.http.*


const val contentType = Constant.CONTENT_TYPE


interface FollowCall{
    //Post para um usuario poder seguir outro usuario
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("follow-user")
    fun folowUser (@Body follow: Follow): Call<String>

    //Post para um usuario deixar de seguir outro usuario
    @DELETE("unfollow-user/")
    fun unFolowUser (@Query("followerId") followerID: Int, @Query("followedId") followedID: Int): Call<String>

}