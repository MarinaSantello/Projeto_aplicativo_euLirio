package com.example.loginpage.API.favorite

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.*
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

        fun countFavoritesAnnouncement(announcementId: Int, quantidadeFavoritos:(CountFavoriteAnnouncement) -> Unit){
            val callCountFavoriteAnnouncement = favoriteCall.countFavoriteAnnouncement(announcementId.toLong())

            callCountFavoriteAnnouncement.enqueue(object:

            Callback<CountFavoriteAnnouncement>{
                override fun onResponse(
                    call: Call<CountFavoriteAnnouncement>,
                    response: Response<CountFavoriteAnnouncement>
                ) {
                    val quantidadeFavoritosShortStorie = response.body() ?: CountFavoriteAnnouncement(
                        idAnuncio = announcementId,
                        qtdeFavoritos = "0"
                    )
                    quantidadeFavoritos.invoke(quantidadeFavoritosShortStorie)
                }

                override fun onFailure(call: Call<CountFavoriteAnnouncement>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }

            )
        }

        fun countFavoritesShortStories(shortStorieId: Int, quantidadeFavoritos:(CountFavoriteShortStorie) -> Unit){
            val callCountFavoriteShortStorie = favoriteCall.countFavoriteShortStorie(shortStorieId.toLong())

            callCountFavoriteShortStorie.enqueue(object:

                Callback<CountFavoriteShortStorie>{
                override fun onResponse(
                    call: Call<CountFavoriteShortStorie>,
                    response: Response<CountFavoriteShortStorie>
                ) {
                    val quantidadeFavoritosShortStorie = response.body()!!
//                        ?: CountFavoriteAnnouncement(
//                        idAnuncio = shortStorieId,
//                        qtdeFavoritos = "0"
//                    )
                    quantidadeFavoritos.invoke(quantidadeFavoritosShortStorie)
                }

                override fun onFailure(call: Call<CountFavoriteShortStorie>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }

            )
        }
    }




}


