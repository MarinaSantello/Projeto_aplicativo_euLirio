package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class parentalRatingsList (
    @SerializedName("parental_ratings")
    var classificacoes: List<Classificacao>
)