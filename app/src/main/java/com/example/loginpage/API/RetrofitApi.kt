package com.example.loginpage.API.user

import com.example.loginpage.constants.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi {

    companion object {
        private lateinit var instance: Retrofit // instância do objeto do Retrofit que fará a conexão com a api

        fun getRetrofit(): Retrofit {

            // :: - pegar a instancia do objeto
            if (!::instance.isInitialized) { // verificando se a instância não foi foi inicializada, para poder ser construída, caso seja true
                instance = Retrofit
                    .Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return instance
        }
    }

}