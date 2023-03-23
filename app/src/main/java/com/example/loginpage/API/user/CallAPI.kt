package com.example.loginpage.API.user

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.User
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
        fun getUser(userID: UserID, userData: (User) -> Unit) {
            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
            val userCall = retrofit.create(UserCall::class.java) // instância do objeto contact
            val callCurrentUser = userCall.getByID(userID.id)

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

}