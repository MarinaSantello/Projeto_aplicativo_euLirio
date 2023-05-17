package com.example.loginpage.API.complaint

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.http.*

interface ComplaintCall {

    @GET("complaint-types")
    fun getComplaintTypes(): Call<List<ComplaintType>>

    /* denuncia de anuncios */
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("report-announcement/{id}")
    fun reportAnnouncement(@Path("id") idUser: Int, @Body complaintAnnouncement: ComplaintAnnoncement): Call<String>


    /* denuncia de historias curtas */
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("report-short-storie/{id}")
    fun reportShortStory(@Path("id") idUser: Int, @Body complaintShortStory: ComplaintShortStory): Call<String>


    /* denuncia de usuarios */
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("report-user/{id}")
    fun reportUser(@Path("id") idUser: Int, @Body complaintUser: ComplaintUser): Call<String>

}