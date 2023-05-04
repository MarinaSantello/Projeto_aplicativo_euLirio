package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Follow(
    @SerializedName("id_segue")
    var idSegue: Int?,
    @SerializedName("id_seguindo")
    var idSeguindo: Int?
)