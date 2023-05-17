package com.example.loginpage.API.recommendation

import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.SQLite.model.UserID
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

        fun getRecommendationByID(idRecommendation: Int, userID: Int, recommendationData: (Recommendation) -> Unit) {
            val callRecommendation = recommendationCall.getRecommendationByID(idRecommendation, userID)

            callRecommendation.enqueue(object :
                Callback<List<Recommendation>> {
                override fun onResponse(call: Call<List<Recommendation>>, response: Response<List<Recommendation>>) {
                    val recommendation = response.body()!![0]

                    recommendationData.invoke(recommendation)
                }

                override fun onFailure(call: Call<List<Recommendation>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

        fun getRecommendationByUserId(userId: Int, recommendationsData:(List<Recommendation>) -> Unit){
            val callRecommendationsByUserId = recommendationCall.getReccomendationByUserId(userId)

            callRecommendationsByUserId.enqueue(object:
                Callback<List<Recommendation>>{
                override fun onResponse(
                    call: Call<List<Recommendation>>,
                    response: Response<List<Recommendation>>
                ) {
                    val recommendation = response.body()!!

                    recommendationsData.invoke(recommendation)
                }

                override fun onFailure(call: Call<List<Recommendation>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }
    }
}