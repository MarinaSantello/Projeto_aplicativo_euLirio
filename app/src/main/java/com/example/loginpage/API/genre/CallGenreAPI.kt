package com.example.loginpage.API.genre

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Genre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallGenreAPI {
    companion object {
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val genreCall = retrofit.create(GenreCall::class.java) // instância do objeto contact
        fun callGetGenre(genres: (List<Genero>) -> Unit) {
            val callGetGenres = genreCall.getAll()

            // Excutar a chamada para o End-point
            callGetGenres.enqueue(object :
                Callback<List<Genero>> {
                override fun onResponse(
                    call: Call<List<Genero>>,
                    response: Response<List<Genero>>
                ) {
                    val generos = response.body()!!

                    Log.i("teste gen", generos[0].nomeGenero)

                    genres.invoke(generos)
                }

                override fun onFailure(call: Call<List<Genero>>, t: Throwable) {
                    Log.i("teste gen err", t.message.toString())
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}