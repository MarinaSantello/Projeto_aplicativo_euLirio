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
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val announcementCall = retrofit.create(AnnouncementCall::class.java) // instância do objeto contact
        fun getAnnouncements(announcementsData: (List<AnnouncementGet>) -> Unit) {
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

        fun getAnnouncement(idAnnouncement: Int, announcementData: (String) -> Unit) {
            val callAnnouncement = announcementCall.getByID(idAnnouncement)

            callAnnouncement.enqueue(object :
                Callback<AnnouncementGet> {
                override fun onResponse(
                    call: Call<AnnouncementGet>,
                    response: Response<AnnouncementGet>
                ) {

//                    val announcement = response.body()!!
//
                    announcementData.invoke(".")

                }

                override fun onFailure(call: Call<AnnouncementGet>, t: Throwable) {

                }
            })
        }

        fun getAllAnnouncementsByGenresUser(userID: Int, announcementsData: (List<AnnouncementGet>) -> Unit) {
            val callAnnouncements = announcementCall.getAllAnnouncementsByGenresUser(userID)

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
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}