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
        fun getShortStories(ShortStoryData: (List<ShortStoryGet>) -> Unit) {
            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
            val announcementCall = retrofit.create(CallShortStory::class.java) // instância do objeto contact
            val callAnnouncements = announcementCall.getAllShortStories()

            callAnnouncements.enqueue(object :
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
    }

}