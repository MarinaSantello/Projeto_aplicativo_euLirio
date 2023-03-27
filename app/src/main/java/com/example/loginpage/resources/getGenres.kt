package com.example.loginpage.resources

import android.util.Log
import com.example.loginpage.API.genre.GenreCall
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Genre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getGenres (genre: (List<Genero>) -> Unit){
    val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
    val genreCall = retrofit.create(GenreCall::class.java) // instância do objeto contact
    val callGetGenres01 = genreCall.getAll()

    // Excutar a chamada para o End-point
    callGetGenres01.enqueue(object :
        Callback<List<Genero>> { // enqueue: usado somente quando o objeto retorna um valor
        override fun onResponse(call: Call<List<Genero>>, response: Response<List<Genero>>) {
            val genres = response.body()!!

            Log.i("teste gen", genres.toString())
            genre.invoke(genres)
        }

        override fun onFailure(call: Call<List<Genero>>, t: Throwable) {
        }
    })
}