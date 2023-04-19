package com.example.loginpage.API.favorite

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.FavoriteAnnouncement
import com.example.loginpage.models.FavoriteShortStorie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallFavoriteAPI(){
    companion object {

        val retrofit = RetrofitApi.getRetrofit()
        val favoriteCall = retrofit.create(FavoriteCall::class.java)
        var retorno = listOf<String>()

        //Função para favoritar um anuncio
        fun favoriteAnnouncement(announcementFavorite: FavoriteAnnouncement): List<String> {
            val callFavoriteAnnouncement = favoriteCall.favoriteAnnouncement(announcementFavorite)

            callFavoriteAnnouncement.enqueue( object :
                retrofit2.Callback<String> {
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


        //Função para favoritar uma historia curta
       fun favoriteShortStorie(shortStorieFavorite: FavoriteShortStorie): List<String>{
           val callFavoriteShortStorie = favoriteCall.favoriteShortStorie(shortStorieFavorite)

            callFavoriteShortStorie.enqueue(object:
                Callback<String>{
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

        //Funçãp para desfavoritar um anuncio
        fun unfavoriteAnnouncement(announcementUnFavorite: FavoriteAnnouncement): List<String> {
            val callunFavoriteAnnouncement = favoriteCall.unFavoriteAnnouncement(announcementUnFavorite)

            callunFavoriteAnnouncement.enqueue( object :
                retrofit2.Callback<String> {
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

        //Função para desfavoritar uma historia curta
        fun unfavoriteShortStorie(shortStorieUnFavorite: FavoriteShortStorie): List<String> {
            val callUnFavoriteShortStorie = favoriteCall.unFavoriteShortStorie(shortStorieUnFavorite)

            callUnFavoriteShortStorie.enqueue( object :
                retrofit2.Callback<String> {
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




    }
}

