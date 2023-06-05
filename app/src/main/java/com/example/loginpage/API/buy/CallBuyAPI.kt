package com.example.loginpage.API.buy

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class CallBuyAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit()
        val buyCall = retrofit.create(BuyCall::class.java)
//        fun buyAnnouncement(idUser: Int, urlstripe: (UrlStripe) -> Unit) {
//            val callBuy = buyCall.buyAnnouncement(idUser)
//
//            callBuy.enqueue(object:
//            )
//        }

        fun buyAnnouncement(idUser: Int, idAnuncio: Buy, urlstripe: (UrlStripe) -> Unit) {
            val callBuy = buyCall.buyAnnouncement(idUser, idAnuncio)

            callBuy.enqueue(object :
                Callback<UrlStripe> {
                override fun onResponse(call: Call<UrlStripe>, response: Response<UrlStripe>) {
                    val status = response.body()!!

                    urlstripe.invoke(status)
                }

                override fun onFailure(call: Call<UrlStripe>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            })
        }

        fun buyAnnouncementsByCarrinho(idUser: Int, listAnnouncement: BuyAnnouncement, urlData: (UrlStripe) -> Unit){
            val callBuyAnnouncementsByCarrinho = buyCall.buyAnnouncementsCarrinho(idUser, listAnnouncement)

            callBuyAnnouncementsByCarrinho.enqueue(object:
            Callback<UrlStripe>{
                override fun onResponse(call: Call<UrlStripe>, response: Response<UrlStripe>) {
                    val status = response.body()!!

                    urlData.invoke(status)
                }
                override fun onFailure(call: Call<UrlStripe>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }


        fun confirmBuyAnnouncement(buyInfos: BuyConfirm, receiveData:(StripeConfirmed) -> Unit){
            val callConfirmBuy = buyCall.confirmBuyAnnouncement(buyInfos)

            callConfirmBuy.enqueue(object:
                Callback<StripeConfirmed>{
                override fun onResponse(
                    call: Call<StripeConfirmed>,
                    response: Response<StripeConfirmed>
                ) {
                    val status = response.body()!!

                    receiveData.invoke(status)

                    Log.i("Nice Compra irmão", response.code()!!.toString())
                }

                override fun onFailure(call: Call<StripeConfirmed>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
            )
        }

        fun confirmBuyAnnouncementsCarrinho(idConfirm:ConfirmBuyCarrinho, receiveData: (StripeConfirmed) -> Unit){
            val callConfirmBuyAnnouncementCarrinho = buyCall.confirmBuyAnnouncementsCarrinho(idConfirm)

            callConfirmBuyAnnouncementCarrinho.enqueue(object:
                Callback<StripeConfirmed>{
                override fun onResponse(
                    call: Call<StripeConfirmed>,
                    response: Response<StripeConfirmed>
                ) {
                    val status = response.body()!!

                    receiveData.invoke(status)

                    Log.i("Nice Compra no carrinho irmão", response.code()!!.toString())
                }

                override fun onFailure(call: Call<StripeConfirmed>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
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