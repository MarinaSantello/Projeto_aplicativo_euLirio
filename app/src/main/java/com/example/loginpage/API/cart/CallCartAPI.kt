package com.example.loginpage.API.cart

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Cart
import com.example.loginpage.models.CartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallCartAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val cartCall = retrofit.create(CartCall::class.java) // instância do objeto contact

        fun putInCart(cart: Cart, statusCode: (Int) -> Unit) {
            val callCart = cartCall.putInCart(cart)

            callCart.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("carrinho respon err", t.message.toString())
                }

            })
        }

        fun getItemsCart(userID: Int, itemsCart: (List<CartData>) -> Unit) {
            val callCart = cartCall.getItemsCart(userID)

            callCart.enqueue(object :
                Callback<List<CartData>> {
                override fun onResponse(
                    call: Call<List<CartData>>,
                    response: Response<List<CartData>>
                ) {
                    val carts = response.body()!!

                    itemsCart.invoke(carts)
                }

                override fun onFailure(call: Call<List<CartData>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}