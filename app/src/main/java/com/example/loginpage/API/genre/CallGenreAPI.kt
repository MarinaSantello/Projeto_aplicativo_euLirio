package com.example.loginpage.API.genre

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.user.UserCall
import com.example.loginpage.models.Genre
import com.example.loginpage.models.Genres
import com.example.loginpage.models.User
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