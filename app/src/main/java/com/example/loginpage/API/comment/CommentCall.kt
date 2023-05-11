package com.example.loginpage.API.comment

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Commit
import com.example.loginpage.models.CommitSS
import com.example.loginpage.models.LikeComment
import retrofit2.Call
import retrofit2.http.*

interface CommentCall {

    //Chamada de todos os comentarios de um anuncio
//    @GET("announcement-comments/id/{id}")
//    fun getAllCommentsAnnouncement(@Path("id")announcementID: Int): Call<List<Commit>>

    //Chamada para listar comentários de anuncio
    @GET("announcement-comments/id/")
    fun getAllCommentsAnnouncement(@Query("announcementId")announcementId: Int, @Query("userId") userId: Int): Call<List<Commit>>

    //Chamada para fazer o post de comentario de um anuncio
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("announcement-comment")
    fun postCommentAnnouncement(@Body commentAnnouncementPost: Commit): Call<String>

    //Chamada apara fazer um delete de um comentario de um anuncio
    @DELETE("delete-announcement-comment/id/")
    fun deleteCommentAnnouncement(@Query("commentId") commentId: Int, @Query("announcementId")announcementId: Int ): Call<String>

    //Chamada para curtir o comentario de um anuncio
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("like-announcement-comment")
    fun postLikeComment(@Body likeCommentBody: LikeComment): Call<String>

    //Chamada para descurtir um comentario de um anuncio
    @DELETE("dislike-announcement-comment/")
    fun dislikeAnnouncementComment(@Query("commentId")commentId: Int, @Query("userId") userId: Int): Call<String>


    /* PEQUENAS HISTÓRIAS */
    //Chamada de todos os comentarios de uma pequena história
    @GET("short-storie-comments/id/{id}")
    fun getAllCommentsShortStory(@Path("id")shortStoryID: Int): Call<List<CommitSS>>

    //Chamada para fazer o post de comentario de uma pequena história
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("short-storie-comment")
    fun postCommentShortStory(@Body commentShortStoryPost: CommitSS): Call<String>

    //Chamada apara fazer um delete de um comentario de uma pequena história
    @DELETE("delete-short-storie-comment/id/")
    fun deleteCommentShortStory(@Query("commentId") commentId: Int, @Query("shortStorieId")shortStoryID: Int): Call<String>

    //Chamada para curtir o comentario de uma pequena história
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("like-short-storie-comment")
    fun postLikeShortStoryComment(@Body likeCommentBody: LikeComment): Call<String>

    //Chamada para descurtir um comentario de uma pequena história
    @DELETE("dislike-short-storie-comment/")
    fun dislikeShortStoryComment(@Query("commentId")commentId: Int, @Query("userId") userId: Int): Call<String>

}