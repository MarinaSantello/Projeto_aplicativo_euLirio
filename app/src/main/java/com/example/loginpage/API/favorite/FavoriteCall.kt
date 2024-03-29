package com.example.loginpage.API.favorite

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.http.*

interface FavoriteCall{
    //Adicionar um novo anuncio
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("favorite-announcement")
    fun favoriteAnnouncement(@Body favorite: FavoriteAnnouncement): Call<String>

    //Desfavoritar alguma historia curta
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("unfavorite-announcement")
    fun unFavoriteAnnouncement(@Body favorite: FavoriteAnnouncement): Call<String>

    //Obter a quantidade total de favoritos que o anuncio recebeu
    @GET("count-announcement-favorites/announcement-id/{id}")
    fun countFavoriteAnnouncement(@Path("id") id: Long): Call<CountFavoriteAnnouncement>

    //Favoritar uma historia curta
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("favorite-short-storie")
    fun favoriteShortStorie(@Body favorite: FavoriteShortStorie): Call<String>

    //Desfavoritar uma historia curta
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("unfavorite-short-storie")
    fun unFavoriteShortStorie(@Body favorite: FavoriteShortStorie): Call<String>

    //Obter a quantidade total de favoritos que a historia curta recebeu
    @GET("count-short-stories-favorites/short-storie-id/{id}")
    fun countFavoriteShortStorie(@Path("id") id: Long): Call<CountFavoriteShortStorie>

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("favorite-recommendation")
    fun favoriteRecommendation(@Body Favorite: likeRecommendation): Call<String>

    @DELETE("unfavorite-recommendation/")
    fun unfavoriteRecommendation(@Query("recommendationId") recommendationId: Int, @Query("userId") userId: Int): Call<String>


}