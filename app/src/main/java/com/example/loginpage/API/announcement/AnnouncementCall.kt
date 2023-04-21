package com.example.loginpage.API.announcement

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.AnnouncementPost
import com.example.loginpage.models.Announcements
import retrofit2.Call
import retrofit2.http.*

interface AnnouncementCall {
    @GET("announcement/id/")
    fun getByID(@Query("announcementId") announcementID: Int, @Query("userId") userID: Int): Call<List<AnnouncementGet>>

    @GET("announcements")
    fun getAllAnnouncements(): Call<List<AnnouncementGet>>

    @GET("announcements/user-id/{id}")
    fun getAllAnnouncementsByGenresUser(@Path("id") userID: Int): Call<List<AnnouncementGet>>

    @GET("activated-announcement/user-id/{id}")
    fun getAllAnnouncementsByUserActivated(@Path("id") userID: Int): Call<List<AnnouncementGet>>

    @GET("desactivated-announcement/user-id/{id}")
    fun getAllAnnouncementsByUserDeactivated(@Path("id") userID: Int): Call<List<AnnouncementGet>>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("announcement")
    fun postAnnouncement(@Body announcementPost: AnnouncementPost): Call<String>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @DELETE("announcement/id/{id}")
    fun deleteAnnouncement(@Path("id") userID: Int): Call<String>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @PUT("announcement/id/{id}")
    fun updateAnnouncement(@Path("id") userID: Int, @Body announcementPost: AnnouncementPost): Call<String>

    @PUT("activate-announcement/id/{id}")
    fun activateAnnouncement(@Path("id") announcementID: Int): Call<String>

    @PUT("desactivate-announcement/id/{id}")
    fun deactivateAnnouncement(@Path("id") announcementID: Int): Call<String>


    @GET("announcement/id/?announcementId={announcementId}&userId={userId}")
    fun statusAnnouncement(@Path("announcementId")announcementId:Long, @Path("userId")userId: Long): Call<List<AnnouncementGet>>
}