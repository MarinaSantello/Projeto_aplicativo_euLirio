package com.example.loginpage.API.complaint

import android.util.Log
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallComplaintAPI {

    companion object {
        val retrofit = RetrofitApi.getRetrofit()
        val complaintCall = retrofit.create(ComplaintCall::class.java)

        fun getComplaintTypes(complaintTypeData: (List<ComplaintType>) -> Unit) {
            val callComplaint = complaintCall.getComplaintTypes()

            callComplaint.enqueue(object :
                Callback<List<ComplaintType>> {
                override fun onResponse(
                    call: Call<List<ComplaintType>>,
                    response: Response<List<ComplaintType>>
                ) {
                    val types = response.body()!!

                    complaintTypeData.invoke(types)
                }

                override fun onFailure(call: Call<List<ComplaintType>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
            )
        }

        fun reportAnnouncement(idUser: Int, complaintAnnouncement: ComplaintAnnoncement, statusCode: (Int) -> Unit) {
            val callComplaint = complaintCall.reportAnnouncement(idUser, complaintAnnouncement)
            Log.i("response denun", idUser.toString())
            Log.i("response denun", complaintAnnouncement.idAnuncio.toString())
            Log.i("response denun", complaintAnnouncement.descricao)
            Log.i("response denun", complaintAnnouncement.tipo.toString())

            callComplaint.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()
                    Log.i("response denun", response.message())

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
            )
        }

        fun reportShortStory(idUser: Int, complaintShortStory: ComplaintShortStory, statusCode: (Int) -> Unit) {
            val callComplaint = complaintCall.reportShortStory(idUser, complaintShortStory)

            callComplaint.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
            )
        }

        fun reportUser(idUser: Int, complaintUser: ComplaintUser, statusCode: (Int) -> Unit) {
            val callComplaint = complaintCall.reportUser(idUser, complaintUser)

            callComplaint.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
            )
        }

        fun reportRecommendation(idUser: Int, complaintRecommendation: ComplaintRecommendation, statusCode: (Int) -> Unit) {
            val callComplaint = complaintCall.reportRecommendation(idUser, complaintRecommendation)

            callComplaint.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
            )
        }
    }
}