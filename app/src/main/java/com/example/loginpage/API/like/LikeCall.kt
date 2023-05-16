package com.example.loginpage.API.like

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.http.*

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

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("like-recommendation")
    fun likeRecommendation(@Body like: likeRecommendation): Call<String>

    @DELETE("dislike-recommendation/")
    fun dislikeRecommendation(@Query("recommendationId") recommendationId: Int, @Query("userId") userID: Int): Call<String>

}