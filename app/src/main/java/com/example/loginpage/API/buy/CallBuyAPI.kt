package com.example.loginpage.API.buy

import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Buy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallBuyAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit()
        val buyCall = retrofit.create(BuyCall::class.java)

        fun buyAnnouncement(buy: Buy, statusCode: (Int) -> Unit) {
            val callBuy = buyCall.buyAnnouncement(buy)

            callBuy.enqueue(object :
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