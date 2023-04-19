package com.example.loginpage.API.announcement

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.AnnouncementPost
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

        fun getAnnouncement(idAnnouncement: Int, userID: Int, announcementData: (AnnouncementGet) -> Unit) {
            val callAnnouncement = announcementCall.getByID(idAnnouncement, userID)

            Log.i("anuncio get", idAnnouncement.toString())

            callAnnouncement.enqueue(object :
                Callback<List<AnnouncementGet>> {
                override fun onResponse(
                    call: Call<List<AnnouncementGet>>,
                    response: Response<List<AnnouncementGet>>
                ) {

                    val announcement = response.body()!![0]

                    announcementData.invoke(announcement)

                }

                override fun onFailure(call: Call<List<AnnouncementGet>>, t: Throwable) {
                    Log.i("get anuncio erro", t.message.toString())
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

        fun getAnnouncmentsByUserActivated(announcementID: Int, announcementsData: (List<AnnouncementGet>) -> Unit) {
            val callAnnouncements = announcementCall.getAllAnnouncementsByUserActivated(announcementID)

            Log.i("id anuncio", announcementID.toString())
            callAnnouncements.enqueue(object :
            Callback<List<AnnouncementGet>> {
                override fun onResponse(
                    call: Call<List<AnnouncementGet>>,
                    response: Response<List<AnnouncementGet>>
                ) {
                    val announcements = response.body()!!

                    Log.i("anuncios usuario", announcements[0].titulo)
                    announcementsData.invoke(announcements)
                }

                override fun onFailure(call: Call<List<AnnouncementGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
            )
        }

        fun postAnnouncement(announcementPost: AnnouncementPost, statusCode: (Int) -> Unit) {
            val callAnnouncement = announcementCall.postAnnouncement(announcementPost)

            callAnnouncement.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()
                    Log.i("retorno api", response.message().toString())

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("retorno api err", t.message.toString())
                }
            })
        }
    }
}