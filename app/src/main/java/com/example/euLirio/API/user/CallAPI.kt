package com.example.euLirio.API.user

import android.util.Log
import com.example.euLirio.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//data class retorno (
//    var code: Int,
//    var message: String
//)

class CallAPI() {

    companion object {
        fun callPost(user: User): List<String> {

            var retorno = listOf<String>()

            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
            val userCall = retrofit.create(UserCall::class.java) // instância do objeto contact
            val callInsertUser = userCall.save(user)

            // Excutar a chamada para o End-point
            callInsertUser.enqueue(object :
                Callback<String> { // enqueue: usado somente quando o objeto retorna um valor
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf<String>(
                        response.message()!!.toString(),
                        response.code()!!.toString()
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