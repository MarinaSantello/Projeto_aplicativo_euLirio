package com.example.loginpage.API.recommendation

import com.example.loginpage.constants.Constant
import com.example.loginpage.models.Recommendation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RecommendationCall {

    @Headers("Content-Type:${Constant.CONTENT_TYPE}")
    @POST("recommendation")
    fun postRecommendation(@Body recommendation: Recommendation): Call<String>
}