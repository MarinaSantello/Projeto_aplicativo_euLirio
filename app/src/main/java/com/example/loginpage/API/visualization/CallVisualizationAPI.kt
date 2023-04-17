package com.example.loginpage.API.visualization

import android.util.Log
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.VisualizationAnnouncement
import com.example.loginpage.models.VisualizationShortStorie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallVisualizationAPI() {
    companion object{
        val retrofit = RetrofitApi.getRetrofit()
        val visualizationCall = retrofit.create(VisualizationCall::class.java)
        var retorno = listOf<String>()


        //Função para visualizar um anuncio
        fun viewAnnouncement(announcementView: VisualizationAnnouncement): List<String>{
            val callViewAnnouncement = visualizationCall.visualizationAnnouncement(announcementView)

            callViewAnnouncement.enqueue(object:
            retrofit2.Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    CallFavoriteAPI.retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    CallFavoriteAPI.retorno = listOf<String>(
                        t.message.toString()
                    )
                    Log.i("respon post", t.message.toString())
                }

            }
            )
            return retorno
        }

        //Função para remover uma visualização de anuncio
        fun unViewAnnouncement(announcementView: VisualizationAnnouncement): List<String>{
            val callUnViewAnnouncement = visualizationCall.unVisualizationAnnouncement(announcementView)

            callUnViewAnnouncement.enqueue(object:
                retrofit2.Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    CallFavoriteAPI.retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    CallFavoriteAPI.retorno = listOf<String>(
                        t.message.toString()
                    )
                    Log.i("respon post", t.message.toString())
                }

            }
            )
            return retorno
        }

        //Função para adicionar uma visualização em uma historia curta
        fun viewShortStorie(shortStorieView: VisualizationShortStorie): List<String>{
            val callViewShortStorie = visualizationCall.visualizationShortStorie(shortStorieView)

            callViewShortStorie.enqueue(object:
                retrofit2.Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    CallFavoriteAPI.retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    CallFavoriteAPI.retorno = listOf<String>(
                        t.message.toString()
                    )
                    Log.i("respon post", t.message.toString())
                }

            }
            )
            return retorno
        }

        //Função para remover uma visualização em uma historia curta
        fun unViewShortStorie(shortStorieUnView: VisualizationShortStorie): List<String>{
            val callUnViewShortStorie = visualizationCall.unVisualizationShortStorie(shortStorieUnView)

            callUnViewShortStorie.enqueue(object:
                retrofit2.Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    CallFavoriteAPI.retorno = listOf<String>(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    CallFavoriteAPI.retorno = listOf<String>(
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