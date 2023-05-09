package com.example.loginpage.API.comment

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Commit
import retrofit2.Call
import retrofit2.http.*

interface CommentCall {

    //Chamada de todos os comentarios de um anuncio
    @GET("announcement-comments/id/{id}")
    fun getAllCommentsAnnouncement(@Path("id")announcementID: Int): Call<List<Commit>>

    //Chamada para fazer o post de comentario de um anuncio
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("announcement-comment")
    fun postCommentAnnouncement(@Body commentAnnouncementPost: Commit): Call<String>

    //Chamada apara fazer um delete de um comentario de um anuncio
    @DELETE("delete-announcement-comment/id/")
    fun deleteCommentAnnouncement(@Query("commentId") commentId: Int, @Query("announcementId")announcementId: Int ): Call<String>
}