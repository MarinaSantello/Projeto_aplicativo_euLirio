package com.example.loginpage.API.search

import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.ShortStoryGet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallSearchaAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val searchCall = retrofit.create(SearchCall::class.java) // instância do objeto contact

        /* * * * AMBOS * * * */
        fun searchPubByGenre(genreName: String, userID: Int, data: (List<AnnouncementGet>?, List<ShortStoryGet>?) -> Unit) {
            val callSearchAnnouncement = searchCall.searchAnnouncementsByGenre(genreName, userID)
            val callSearchShortStory = searchCall.searchShortStoriesByGenre(genreName, userID)

            callSearchAnnouncement.enqueue(object :
                Callback<List<AnnouncementGet>> {
                override fun onResponse(
                    call: Call<List<AnnouncementGet>>,
                    response: Response<List<AnnouncementGet>>
                ) {
                    val announcements = response.body()

                    callSearchShortStory.enqueue(object :
                        Callback<List<ShortStoryGet>> {
                        override fun onResponse(
                            call: Call<List<ShortStoryGet>>,
                            response: Response<List<ShortStoryGet>>
                        ) {
                            val ss = response.body()

                            data.invoke(announcements, ss)
                        }

                        override fun onFailure(call: Call<List<ShortStoryGet>>, t: Throwable) {
                            //TODO("Not yet implemented")
                        }
                    })
                }

                override fun onFailure(call: Call<List<AnnouncementGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

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


        /* * * * PEQUENAS HISTÓRIAS * * * */
        fun searchShortStoriesByName(shortStorieTitle: String, userID: Int, ShortStoriesData: (List<ShortStoryGet>?) -> Unit) {
            val callSearch = searchCall.searchShortStoriesByName(shortStorieTitle, userID)

            callSearch.enqueue(object :
                Callback<List<ShortStoryGet>> {
                override fun onResponse(
                    call: Call<List<ShortStoryGet>>,
                    response: Response<List<ShortStoryGet>>
                ) {
                    val ss = response.body()

                    ShortStoriesData.invoke(ss)
                }

                override fun onFailure(call: Call<List<ShortStoryGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}