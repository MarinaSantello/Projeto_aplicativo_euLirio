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

            callAnnouncement.enqueue(object :
                Callback<List<AnnouncementGet>> {
                override fun onResponse(
                    call: Call<List<AnnouncementGet>>,
                    response: Response<List<AnnouncementGet>>
                ) {

                    val announcement = response.body()?.get(0)

                    if (announcement != null) announcementData.invoke(announcement)

                }

                override fun onFailure(call: Call<List<AnnouncementGet>>, t: Throwable) {
                    Log.i("get anuncio erro", t.message.toString())
                }
            })
        }

        fun getAllAnnouncementsByGenresUser(userID: Int, announcementsData: (List<AnnouncementGet>?) -> Unit) {
            val callAnnouncements = announcementCall.getAllAnnouncementsByGenresUser(userID)

            callAnnouncements.enqueue(object :
                Callback<List<AnnouncementGet>> {
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

        fun getAnnouncementsByUser(type: Int, announcementID: Int, announcementsData: (List<AnnouncementGet>?) -> Unit) {
            val callAnnouncements = if(type == 1) announcementCall.getAllAnnouncementsByUserActivated(announcementID) else announcementCall.getAllAnnouncementsByUserDeactivated(announcementID)

            Log.i("id anuncio", announcementID.toString())
            callAnnouncements.enqueue(object :
            Callback<List<AnnouncementGet>> {
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

        fun deleteAnnouncement(announcementID: Int, statusCode: (Int) -> Unit) {
            val callAnnouncement = announcementCall.deleteAnnouncement(announcementID)

            callAnnouncement.enqueue(object :
            Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()
                    Log.i("apagar", status.toString())

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

        fun updateAnnouncement(announcementID: Int, announcement: AnnouncementPost, statusCode: (Int) -> Unit) {
            val callAnnouncement = announcementCall.updateAnnouncement(announcementID, announcement)

            callAnnouncement.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    Log.i("teste update", status.toString())

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

        fun statusAnnouncement(type: Int, announcementID: Int, apiReturn: (Int, String) -> Unit) {
            val callAnnouncement = if (type == 1) announcementCall.deactivateAnnouncement(announcementID) else announcementCall.activateAnnouncement(announcementID)

            Log.i("id anun", announcementID.toString())
            callAnnouncement.enqueue(object :
            Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()
                    val message = response.body()!!

                    apiReturn.invoke(status, message)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            })
        }

        fun getUserFavoritedAnnouncements(userID: Int, announcementsData: (List<AnnouncementGet>?) -> Unit) {
            val callAnnouncement = announcementCall.getUserFavoritedAnnouncements(userID)

            callAnnouncement.enqueue(object :
                Callback<List<AnnouncementGet>> {
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

        fun getUserReadedAnnouncements(userID: Int, announcementsData: (List<AnnouncementGet>?) -> Unit) {
            val callAnnouncement = announcementCall.getUserReadedAnnouncements(userID)

            callAnnouncement.enqueue(object :
                Callback<List<AnnouncementGet>> {
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