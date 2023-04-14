package com.example.loginpage.API.like

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.LikeAnnouncement
import com.example.loginpage.models.LikeShortStorie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallLikeAPI() {
    companion object {
        val retrofit = RetrofitApi.getRetrofit()
        val likeCall = retrofit.create(LikeCall::class.java)
        var retorno = listOf<String>()

        fun likeAnnouncement(announcementLike: LikeAnnouncement): List<String> {
            val callLikeAnnouncement = likeCall.likeAnnouncement(announcementLike)

            callLikeAnnouncement.enqueue( object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    retorno = listOf<String>(
                        t.message.toString()
                    )

                    Log.i("respon post", t.message.toString())
                }

            }
            )

            return retorno
        }

        fun likeShortStorie(shortStorieLike: LikeShortStorie): List<String> {
            val callShortStorieAnnouncement = likeCall.likeShortStorie(shortStorieLike)

            callShortStorieAnnouncement.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    retorno = listOf<String>(
                        t.message.toString()
                    )

                    Log.i("respon post", t.message.toString())
                }

            }
            )

            return retorno
        }

        fun dislikeAnnouncement(announcementDislike: LikeAnnouncement): List<String> {
            val callDislikeAnnouncement = likeCall.dislikeAnnouncement(announcementDislike)

            callDislikeAnnouncement.enqueue(object:
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    retorno = listOf<String>(
                        t.message.toString()
                    )

                    Log.i("respon post", t.message.toString())
                }

            }
            )

            return retorno
        }

        fun dislikeShortStorie(shortStorieDislike: LikeShortStorie): List<String> {
            val callDislikeShortStorie = likeCall.dislikeShortStorie(shortStorieDislike)

            callDislikeShortStorie.enqueue(object:
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    retorno = listOf<String>(
                        t.message.toString()
                    )

                    Log.i("respon post", t.message.toString())
                }

            }
            )

            return retorno
        }

        fun verifyAnnouncementLike(announcementId: Int, userId: Int): Boolean {
            val callVerifyAnnouncementLike = likeCall.verifyAnnouncementLike(announcementId, userId)
            var teste: Boolean = false

            callVerifyAnnouncementLike.enqueue(object:
                Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    teste =  response.message().toBoolean()
                    Log.i("respon post", response.message().toString())
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
            )

            return teste
        }
    }
}