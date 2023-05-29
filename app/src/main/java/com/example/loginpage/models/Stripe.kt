package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class UrlStripe(
    var url: String,
    @SerializedName("intent_id")
    var intentId: String
)