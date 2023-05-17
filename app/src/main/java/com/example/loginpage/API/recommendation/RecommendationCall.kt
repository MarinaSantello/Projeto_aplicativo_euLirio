package com.example.loginpage.API.recommendation

import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Recommendation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecommendationCall {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("recommendation")
    fun postRecommendation(@Body recommendation: Recommendation): Call<String>

    @GET("recommendation/id/")
    fun getRecommendationByID(@Query("recommendationId") idRecommendation: Int, @Query("userId") userID: Int): Call<List<Recommendation>>


    //Listar as recomendações de quem o usuário segue
    @GET("recommendations/user-id/{id}")
    fun getReccomendationByUserId(@Path("id") userID: Int): Call<List<Recommendation>>

}