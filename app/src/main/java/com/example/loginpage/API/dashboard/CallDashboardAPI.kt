package com.example.loginpage.API.dashboard

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.Dashboard
import retrofit2.Call
import retrofit2.Response

class CallDashboardAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val dashboardCall = retrofit.create(DashboardCall::class.java) // instância do objeto contact

        fun getDataDashboard(announcementId: Int, DashboardData: (Dashboard) -> Unit) {
            val callDashboard = dashboardCall.getDataDashboard(announcementId)

            callDashboard.enqueue(object :
                retrofit2.Callback<Dashboard> {
                override fun onResponse(call: Call<Dashboard>, response: Response<Dashboard>) {
                    val dataDashboard = response.body()!!
                    Log.i("teste dash", dataDashboard.roundedData.toString())

                    DashboardData.invoke(dataDashboard)
                }

                override fun onFailure(call: Call<Dashboard>, t: Throwable) {
                    Log.i("teste dash", t.message.toString())
                }
            })
        }

    }
}