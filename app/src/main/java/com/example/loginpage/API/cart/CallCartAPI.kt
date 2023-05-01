package com.example.loginpage.API.cart

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Cart
import com.example.loginpage.models.CartData
import com.example.loginpage.models.CartList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallCartAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val cartCall = retrofit.create(CartCall::class.java) // instância do objeto contact

        fun putInCart(userID: Int, cart: Cart, statusCode: (Int) -> Unit) {
            val callCart = cartCall.putInCart(userID, cart)

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

        fun buyItemsCart(userID: Int, cart: Cart, statusCode: (Int) -> Unit) {
            val callCart = cartCall.buyItemsCart(userID, cart)

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
        fun getItemsCart(userID: Int, itemsCart: (CartList?) -> Unit) {
            val callCart = cartCall.getItemsCart(userID)

            callCart.enqueue(object :
                Callback<CartList> {
                override fun onResponse(
                    call: Call<CartList>,
                    response: Response<CartList>
                ) {
                    val carts = response.body()

                    itemsCart.invoke(carts)
                }

                override fun onFailure(call: Call<CartList>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }
}