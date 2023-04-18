package com.example.loginpage.API.parentalRatings

import com.example.loginpage.models.parentalRatingsList
import retrofit2.Call
import retrofit2.http.GET

interface ParentalRatingsCall {

    @GET ("parental-ratings")
    fun getClassificacoes(): Call<parentalRatingsList>
}