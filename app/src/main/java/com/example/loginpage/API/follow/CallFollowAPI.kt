package com.example.loginpage.API.follow

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Follow
import com.example.loginpage.models.User
import com.example.loginpage.models.UserFollow
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
            val callFollowUser = followCall.followUser(followUser)

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
            val callUnFollowUser = followCall.unFollowUser(followerId, followedID)

            callUnFollowUser.enqueue(object:
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



        fun getFollowers(userID: Int, currentUser: Int, followersData: (List<UserFollow>?) -> Unit) {
            Log.i("pesquisado", userID.toString())
            Log.i("pesquisando", currentUser.toString())
            val callFollow = followCall.getFollowers(userID, currentUser)

            callFollow.enqueue(object :
                Callback<List<UserFollow>>{
                override fun onResponse(
                    call: Call<List<UserFollow>>,
                    response: Response<List<UserFollow>>
                ) {
                    val followers = response.body()

                    followersData.invoke(followers)
                }

                override fun onFailure(call: Call<List<UserFollow>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

        fun getFollowing(userID: Int, currentUSer: Int, followersData: (List<UserFollow>?) -> Unit) {
            val callFollow = followCall.getFollowing(userID, currentUSer)

            callFollow.enqueue(object :
                Callback<List<UserFollow>>{
                override fun onResponse(
                    call: Call<List<UserFollow>>,
                    response: Response<List<UserFollow>>
                ) {
                    val followers = response.body()

                    followersData.invoke(followers)
                }

                override fun onFailure(call: Call<List<UserFollow>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}