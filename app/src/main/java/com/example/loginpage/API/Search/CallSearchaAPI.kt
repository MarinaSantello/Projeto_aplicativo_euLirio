package com.example.loginpage.API.Search

import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.AnnouncementGet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallSearchaAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val searchCall = retrofit.create(SearchCall::class.java) // instância do objeto contact

        /* * * * ANUNCIOS * * * */
        fun searchAnnouncementsByName(announcementTitle: String, userID: Int, AnnouncementsData: (List<AnnouncementGet>?) -> Unit) {
            val callSearch = searchCall.searchAnnouncementsByName(announcementTitle, userID)

            callSearch.enqueue(object :
                Callback<List<AnnouncementGet>> {
                override fun onResponse(
                    call: Call<List<AnnouncementGet>>,
                    response: Response<List<AnnouncementGet>>
                ) {
                    val announcements = response.body()

                    AnnouncementsData.invoke(announcements)
                }

                override fun onFailure(call: Call<List<AnnouncementGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}