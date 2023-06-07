package com.example.loginpage.API.dashboard

import com.example.loginpage.models.Dashboard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardCall {

    @GET("show-dashboard-infos/announcement-id/{id}")
    fun getDataDashboard(@Path("id") idAnuncio: Int): Call<Dashboard>
}