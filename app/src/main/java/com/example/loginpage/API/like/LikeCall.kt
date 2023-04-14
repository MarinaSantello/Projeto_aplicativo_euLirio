package com.example.loginpage.API.like

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

const val contentType = Constant.CONTENT_TYPE

interface LikeCall {
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("like-announcement")
    fun likeAnnouncement(@Body like: LikeAnnouncement): Call<String>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("like-short-storie")
    fun likeShortStorie(@Body like: LikeShortStorie): Call<String>

    @GET("count-announcement-likes/announcement-id/{id}")
    fun countAnnouncementLikes(@Path("id") id: Long): Call<CountAnnouncementLikes>

    @GET("count-short-stories-likes/short-storie-id/{id}")
    fun countShortStorieLikes(@Path("id") id: Long): Call<CountShortStorieLikes>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("dislike-announcement")
    fun dislikeAnnouncement(@Body dislike: LikeAnnouncement): Call<String>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("dislike-short-storie")
    fun dislikeShortStorie(@Body dislike: LikeShortStorie): Call<String>

    @GET("verify-announcement-like")
    fun verifyAnnouncementLike(@Query("announcementID") announcementID: Int, @Query("userId") userID: Int): Call<Boolean>
}