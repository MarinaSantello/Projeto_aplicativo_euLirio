package com.example.loginpage.API.visualization

import android.util.Log
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.*
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
                    retorno = listOf<String>(
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

        //Função para mostrar a quantidade de visualizações de um anuncio
        fun countViewAnnouncement(announcementId: Int, quantidadeViews:(CountVisualizationAnnouncement) -> Unit){
            val callCountViewsAnnouncement = visualizationCall.countViewsAnnouncement(announcementId.toLong())

            callCountViewsAnnouncement.enqueue(object:
            Callback<CountVisualizationAnnouncement>{
                override fun onResponse(
                    call: Call<CountVisualizationAnnouncement>,
                    response: Response<CountVisualizationAnnouncement>
                ) {
                    val quantidadeViewsAnnouncement = if (response.code() == 200) response.body()!! else CountVisualizationAnnouncement(
                        idAnuncio = announcementId,
                        qtdeLidos = "0"
                    )
                    quantidadeViews.invoke(quantidadeViewsAnnouncement)
                    Log.i("anun id", response.message().toString())
                }

                override fun onFailure(call: Call<CountVisualizationAnnouncement>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            })
        }


        //Função para mostrar a quantidade de visualizações de uma historia curta
        fun countViewShortStorie(shortStoryId:Int, qtdeViews:(CountVisualizationShortStorie) -> Unit){
            val callCountVisualizationShortStorie = visualizationCall.countViewsShortStorie(shortStoryId.toLong())

            callCountVisualizationShortStorie.enqueue(object:
            Callback<CountVisualizationShortStorie>{
                override fun onResponse(
                    call: Call<CountVisualizationShortStorie>,
                    response: Response<CountVisualizationShortStorie>
                ) {
                    val quantidadeViewsShortStorie = response.body() ?: CountVisualizationShortStorie(
                        idHistoriaCurta = shortStoryId,
                        qtdeLidos = "0"
                    )

                    qtdeViews.invoke(quantidadeViewsShortStorie)
                }

                override fun onFailure(call: Call<CountVisualizationShortStorie>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            }
            )
        }



    }

}