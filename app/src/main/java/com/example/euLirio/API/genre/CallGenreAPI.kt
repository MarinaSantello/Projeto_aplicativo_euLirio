package com.example.euLirio.API.genre

import com.example.euLirio.API.user.RetrofitApi
import com.example.euLirio.models.Genre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallGenreAPI {

    companion object {
        fun callGetGenre(): List<Genre> {

            var retorno = listOf<Genre>()

            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
            val genreCall = retrofit.create(GenreCall::class.java) // instância do objeto contact
            val callGetGenres = genreCall.getAll()

            // Excutar a chamada para o End-point
            callGetGenres.enqueue(object :
                Callback<List<Genre>> { // enqueue: usado somente quando o objeto retorna um valor
                override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
                    retorno = response.body()!!
                }

                override fun onFailure(call: Call<List<Genre>>, t: Throwable) {

                }
            })

            return retorno
        }
    }
}