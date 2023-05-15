package com.example.loginpage.API.recommendation

import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Recommendation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallRecommendationAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit()
        val recommendationCall = retrofit.create(RecommendationCall::class.java)

        fun postRecommendation(recommendation: Recommendation, statusCode: (Int) -> Unit) {
            val callRecommendation = recommendationCall.postRecommendation(recommendation)

            callRecommendation.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}