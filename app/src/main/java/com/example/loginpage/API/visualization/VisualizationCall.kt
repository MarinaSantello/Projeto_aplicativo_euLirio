package com.example.loginpage.API.visualization

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.CountFavoriteAnnouncement
import com.example.loginpage.models.CountFavoriteShortStorie
import com.example.loginpage.models.VisualizationAnnouncement
import com.example.loginpage.models.VisualizationShortStorie
import retrofit2.Call
import retrofit2.http.*

interface VisualizationCall {
    //Adicionar a visualização do anuncio
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("mark-announcement-as-read")
    fun visualizationAnnouncement(@Body visualization: VisualizationAnnouncement): Call<String>

    //Remover uma visualização do anuncio
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("unread-announcement")
    fun unVisualizationAnnouncement(@Body visualization: VisualizationAnnouncement): Call<String>

    //Obter a quantidade total de visualizações que o anuncio recebeu
    @GET("count-announcement-reads/announcement-id/{id}")
    fun countViewsAnnouncement(@Path("id") id: Long): Call<CountFavoriteAnnouncement>

    //Adicionar a visualização da historia curta
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("mark-storie-as-read")
    fun visualizationShortStorie(@Body visualization: VisualizationShortStorie): Call<String>

    //Remover uma visualização da historia curta
    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("unread-storie")
    fun unVisualizationShortStorie(@Body visualization: VisualizationShortStorie): Call<String>

    //Obter a quantidade total de visualizações que a historia curta recebeu
    @GET("count-short-stories-reads/announcement-id/{id}")
    fun countViewsShortStorie(@Path("id") id: Long): Call<CountFavoriteShortStorie>


}