package com.example.loginpage.API.comment

import android.util.Log
import com.example.loginpage.API.favorite.FavoriteCall
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Commit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallCommentAPI() {

    companion object{
        val retrofit = RetrofitApi.getRetrofit()
        val commentCall = retrofit.create(CommentCall::class.java)
        var retorno = listOf<String>()

        fun getCommentsAnnouncement(idAnnouncement: Int, commentsData:(List<Commit>) -> Unit){
            val callCommentsAnnouncement = commentCall.getAllCommentsAnnouncement(idAnnouncement)

            callCommentsAnnouncement.enqueue(object:

            Callback<List<Commit>>{
                override fun onResponse(
                    call: Call<List<Commit>>,
                    response: Response<List<Commit>>
                ) {
                    val comments = response.body()!!

                    commentsData.invoke(comments)
                }

                override fun onFailure(call: Call<List<Commit>>, t: Throwable) {
                    Log.i("get comment erro", t.message.toString())
                }
            }
            )
        }
    }
}