package com.example.loginpage.API.buy

import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.AnnouncementGet
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

        fun getPurchasedAnnouncement(userID: Int, announcementsData: (List<AnnouncementGet>?) -> Unit) {
            val callBuy = buyCall.getPurchasedAnnouncements(userID)

            callBuy.enqueue(object :
                Callback<List<AnnouncementGet>>{
                override fun onResponse(
                    call: Call<List<AnnouncementGet>>,
                    response: Response<List<AnnouncementGet>>
                ) {
                    val announcements = response.body()

                    announcementsData.invoke(announcements)
                }

                override fun onFailure(call: Call<List<AnnouncementGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

    }
}