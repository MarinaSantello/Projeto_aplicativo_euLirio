package com.example.loginpage.API.stripe

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.BuyAnnouncement
import com.example.loginpage.models.UrlStripe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CallStripeAPI(){
    companion object{
        val retrofit = RetrofitApi.getRetrofit()
        val retorno = listOf<String>()
        var callStripe = retrofit.create(CallStripe::class.java)


        fun buyAnnouncementStripe(UserId: Int, IdAnnouncement: BuyAnnouncement, urlStripeData:(UrlStripe) -> Unit){
            val callBuyAnnouncementStripe = callStripe.callUrlStripe(UserId, IdAnnouncement)

            callBuyAnnouncementStripe.enqueue(object:

                Callback<UrlStripe>{
                override fun onResponse(call: Call<UrlStripe>, response: Response<UrlStripe>) {
                    Log.i("pagamento", response.toString())
                    val url = response.body()

                    urlStripeData.invoke(url!!)

                    Log.i("comprou", response.code()!!.toString())
                }

                override fun onFailure(call: Call<UrlStripe>, t: Throwable) {
                    TODO("Not yet implemented")

                    Log.i("deu ruim", t.message.toString())
                }
            }
            )
        }
    }
}