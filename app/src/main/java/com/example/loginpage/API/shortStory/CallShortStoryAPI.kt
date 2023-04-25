package com.example.loginpage.API.shortStory

import com.example.loginpage.API.announcement.AnnouncementCall
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.ShortStoryGet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallShortStoryAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val shortStoryCall = retrofit.create(CallShortStory::class.java) // instância do objeto contact

        fun getShortStories(ShortStoryData: (List<ShortStoryGet>) -> Unit) {
            val callShortStories = shortStoryCall.getAllShortStories()

            callShortStories.enqueue(object :
                Callback<List<ShortStoryGet>> {
                override fun onResponse(
                    call: Call<List<ShortStoryGet>>,
                    response: Response<List<ShortStoryGet>>
                ) {
                   val shortStories = response.body()!!

                    ShortStoryData.invoke(shortStories)
                }

                override fun onFailure(call: Call<List<ShortStoryGet>>, t: Throwable) {

                }
            })
        }

        fun getShortStoriesByGenreUser(userID: Int, ShortStoryData: (List<ShortStoryGet>) -> Unit) {
            val callShortStories = shortStoryCall.getAllShortStoriesByGenreUser(userID)

            callShortStories.enqueue(object :
                Callback<List<ShortStoryGet>>{
                override fun onResponse(
                    call: Call<List<ShortStoryGet>>,
                    response: Response<List<ShortStoryGet>>
                ) {
                    val shortStories = response.body()!!

                    ShortStoryData.invoke(shortStories)
                }

                override fun onFailure(call: Call<List<ShortStoryGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            })
        }

        fun getShortStoryByID(shortStoryID: Int, userID: Int, ShortStoryData: (ShortStoryGet) -> Unit) {
            val callShortStories = shortStoryCall.getByID(shortStoryID, userID)

            callShortStories.enqueue(object :
                Callback<List<ShortStoryGet>> {
                override fun onResponse(
                    call: Call<List<ShortStoryGet>>,
                    response: Response<List<ShortStoryGet>>
                ) {
                    val shortStory = response.body()!![0]

                    ShortStoryData.invoke(shortStory)
                }

                override fun onFailure(call: Call<List<ShortStoryGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

        fun getShortStoriesByUser (type: Int, userID: Int, ShortStoryData: (List<ShortStoryGet>) -> Unit) {
            val callShortStories = if(type == 1) shortStoryCall.getAllShortStoriesByUserActivated(userID) else shortStoryCall.getAllShortStoriesByUserDeactivated(userID)

            callShortStories.enqueue(object :
                Callback<List<ShortStoryGet>> {
                override fun onResponse(
                    call: Call<List<ShortStoryGet>>,
                    response: Response<List<ShortStoryGet>>
                ) {
                    val ss = response.body()!!

                    ShortStoryData.invoke(ss)
                }

                override fun onFailure(call: Call<List<ShortStoryGet>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }

}