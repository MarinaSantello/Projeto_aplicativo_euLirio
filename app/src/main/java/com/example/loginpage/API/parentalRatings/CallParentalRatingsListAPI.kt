package com.example.loginpage.API.parentalRatings

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Classificacao
import com.example.loginpage.models.parentalRatingsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallParentalRatingsListAPI {
    companion object {
        val retrofit = RetrofitApi.getRetrofit()
        val parentalRatingsCall = retrofit.create(ParentalRatingsCall::class.java)

        fun getClassificacoes(classificacoes: (List<Classificacao>) -> Unit) {
            val callClassificacoes = parentalRatingsCall.getClassificacoes()

            callClassificacoes.enqueue(object : Callback<parentalRatingsList> {
                override fun onResponse(
                    call: Call<parentalRatingsList>,
                    response: Response<parentalRatingsList>
                ) {
                    val classificacoesAPI = response.body()!!.classificacoes

                    classificacoes.invoke(classificacoesAPI)
                }

                override fun onFailure(call: Call<parentalRatingsList>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            })
        }
    }
}