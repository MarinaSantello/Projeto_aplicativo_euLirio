package com.example.loginpage.API.follow

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Follow
import com.example.loginpage.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallFollowAPI() {
    companion object{

        val retrofit = RetrofitApi.getRetrofit()
        val followCall = retrofit.create(FollowCall::class.java)
        var retorno = listOf<String>()


        //Função para o usuario seguir um autor
        fun followUser(followUser: Follow): List<String>{
            val callFollowUser = followCall.folowUser(followUser)

            callFollowUser.enqueue(object:
                retrofit2.Callback<String>{
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


        //Função para o usuario deixar de seguir um usuario
        fun unfollowUser(followerId: Int, followedID: Int): List<String>{
            val callunFollowUser = followCall.unFolowUser(followerId, followedID)

            callunFollowUser.enqueue(object:
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
    }
}