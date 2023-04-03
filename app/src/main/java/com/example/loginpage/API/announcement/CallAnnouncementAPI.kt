package com.example.loginpage.API.announcement

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.Announcements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CallAnnouncementAPI() {

    companion object {
        fun getAnnouncements(announcementsData: (List<AnnouncementGet>) -> Unit) {
            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
            val announcementCall = retrofit.create(AnnouncementCall::class.java) // instância do objeto contact
            val callAnnouncements = announcementCall.getAllAnnouncements()

            callAnnouncements.enqueue(object :
                Callback<List<AnnouncementGet>> {
                override fun onResponse(
                    call: Call<List<AnnouncementGet>>,
                    response: Response<List<AnnouncementGet>>
                ) {
                    val announcements = response.body()!!

                    announcementsData.invoke(announcements)
                }

                override fun onFailure(call: Call<List<AnnouncementGet>>, t: Throwable) {

                }
            })
        }

//        fun getAnnouncement(idAnnouncement: Int, announcementData: (AnnouncementGet) -> Unit) {
//            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
//            val announcementCall = retrofit.create(AnnouncementCall::class.java) // instância do objeto contact
//            val callAnnouncement = announcementCall.getByID(idAnnouncement)
//
//            callAnnouncement.enqueue(object :
//                Callback<AnnouncementGet> {
//                override fun onResponse(
//                    call: Call<AnnouncementGet>,
//                    response: Response<AnnouncementGet>
//                ) {
//
//                    val announcement = response.body()!!
//
//                    announcementData.invoke(announcement)
//
//                }
//
//                override fun onFailure(call: Call<AnnouncementGet>, t: Throwable) {
//
//                }
//            })
//        }
    }
}