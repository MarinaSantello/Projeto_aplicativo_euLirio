package com.example.loginpage.resources

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.userLogin.UserLoginCall
import com.example.loginpage.Home
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.RetornoApi
import com.example.loginpage.models.UserLogin
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun authenticate(email: String, password: String, context: Context) {

    // obtendo a instancia do firebase
    val auth = FirebaseAuth.getInstance()

    // autenticação
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {

            if (it.isSuccessful) {
                val userLogin = UserLogin(
                    uid = it.result.user!!.uid
                )

                val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
                val userLoginCall = retrofit.create(UserLoginCall::class.java) // instância do objeto contact
                val callValidateUser = userLoginCall.validate(userLogin)

                var responseValidate = 0
                // Excutar a chamada para o End-point
                callValidateUser.enqueue(object :
                    Callback<RetornoApi> { // enqueue: usado somente quando o objeto retorna um valor
                    override fun onResponse(
                        call: Call<RetornoApi>,
                        response: Response<RetornoApi>
                    ) {
                        responseValidate = response.code()

                        if (responseValidate == 200){
                            // registrando o id do usuário no sqlLite
                            val userIDRepository = UserIDrepository(context)
                            userIDRepository.save(UserID(response.body()!!.id))

                            val intent = Intent(context, Home::class.java)
                            context.startActivity(intent)
                        }
                    }

                    override fun onFailure(
                        call: Call<RetornoApi>,
                        t: Throwable
                    ) {
                        val err = t.message
                        Log.i("teste login", err.toString())
                    }
                })
            }
            Log.i("resposta firebase", it.isSuccessful.toString()) // retorna um booleano com a autenticação
        }

}