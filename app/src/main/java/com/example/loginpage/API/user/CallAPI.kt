package com.example.loginpage.API.user

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.User
import com.example.loginpage.models.UserName
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
        fun getUser(userID: Long, userData: (User) -> Unit) {
            Log.i("user id", userID.toString())
            if (userID.toInt() != 0) {
                val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
                val userCall = retrofit.create(UserCall::class.java) // instância do objeto contact
                val callCurrentUser = userCall.getByID(userID, userID)

                callCurrentUser.enqueue(object :
                    Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val user = response.body()!!

                        userData.invoke(user)
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                    }
                })
            }
        }

        fun getNavUser(navUserID: Long, userID: Long, userData: (User) -> Unit) {
            if (userID.toInt() != 0) {
                val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
                val userCall = retrofit.create(UserCall::class.java) // instância do objeto contact
                val callCurrentUser = userCall.getByID(navUserID, userID)

                callCurrentUser.enqueue(object :
                    Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val user = response.body()!!

                        userData.invoke(user)
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                    }
                })
            }
        }
        fun verifyUsername(username: String, validUsername: (Boolean) -> Unit) {
            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
            val userCall = retrofit.create(UserCall::class.java) // instância do objeto contact
            val callVerify = userCall.verifyUserName(username)

            callVerify.enqueue(object :
                Callback<UserName> {
                override fun onResponse(call: Call<UserName>, response: Response<UserName>) {
                    val validate = response.code()

                    Log.i("verificar user", validate.toString())
                    Log.i("verificar user", response.message().toString())

                    if (validate == 200)
                        validUsername.invoke(true)
                    else if (validate == 404)
                        validUsername.invoke(false)
                }

                override fun onFailure(call: Call<UserName>, t: Throwable) {
//                    TODO("Not yet implemented")
                }
            })
        }
    }

}