package com.example.loginpage.API.comment

import android.util.Log
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.favorite.FavoriteCall
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.Commit
import com.example.loginpage.models.CommitSS
import com.example.loginpage.models.LikeAnnouncement
import com.example.loginpage.models.LikeComment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallCommentAPI() {

    companion object{
        val retrofit = RetrofitApi.getRetrofit()
        val commentCall = retrofit.create(CommentCall::class.java)
        var retorno = listOf<String>()

        fun getCommentsAnnouncement(idAnnouncement: Int, userID: Int, commentsData:(List<Commit>?) -> Unit){
            val callCommentsAnnouncement = commentCall.getAllCommentsAnnouncement(idAnnouncement, userID)

            callCommentsAnnouncement.enqueue(object:

            Callback<List<Commit>>{
                override fun onResponse(
                    call: Call<List<Commit>>,
                    response: Response<List<Commit>>
                ) {
                    val comments = response.body()

                    commentsData.invoke(comments)

                    Log.i("get comment erro", response.message().toString())
                }

                override fun onFailure(call: Call<List<Commit>>, t: Throwable) {
                    Log.i("get comment erro", t.message.toString())
                }
            }
            )
        }

        fun postComment(commit: Commit, statusCode: (Int) -> Unit) {
            Log.i("resposta api commit", commit.toString())
            val callCommit = commentCall.postCommentAnnouncement(commit)

            callCommit.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    Log.i("resposta api commit", response.message())
                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

        fun deleteComment(commentId: Int, announcementId: Int): List<String>{
            val callDeleteComment = commentCall.deleteCommentAnnouncement(commentId, announcementId)

            callDeleteComment.enqueue(object:
            Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("resposta api delete", response.message())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    retorno = listOf<String>(
                        t.message.toString()
                    )
                    Log.i("delete erro", t.message.toString())
                }
            }
            )

            return retorno
        }


        fun likeCommentAnnouncement(likeCommentAnnouncement: LikeComment): List<String>{
            val callLikeCommentAnnouncement = commentCall.postLikeComment(likeCommentAnnouncement)

            callLikeCommentAnnouncement.enqueue(object:
                Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post like", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    retorno = listOf<String>(
                        t.message.toString()
                    )
                    Log.i("erro", t.message.toString())
                }
            }
            )
            return  retorno

        }

        fun dislikeCommentAnnouncement(commentId: Int, userID: Int): List<String>{
            val callDislikeCommentAnnouncement = commentCall.dislikeAnnouncementComment(commentId, userID)

            callDislikeCommentAnnouncement.enqueue(object:
                Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    retorno = listOf(
                        response.message().toString(),
                        response.code().toString()
                    )
                    Log.i("respon post like", response.code()!!.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    retorno = listOf<String>(
                        t.message.toString()
                    )
                    Log.i("delete erro", t.message.toString())
                }

            }
            )
            return  retorno
        }

        /* HISTÓRIA CURTA */
        fun getCommentsSS(shortStoryID: Int, userID: Int, commentsData: (List<CommitSS>?) -> Unit) {
            val callComment = commentCall.getAllCommentsShortStory(shortStoryID, userID)

            callComment.enqueue(object :
                Callback<List<CommitSS>> {
                override fun onResponse(
                    call: Call<List<CommitSS>>,
                    response: Response<List<CommitSS>>
                ) {
                    val comments = response.body()

                    commentsData.invoke(comments)
                }

                override fun onFailure(call: Call<List<CommitSS>>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            })
        }
        fun postCommentSS(commit: CommitSS, statusCode: (Int) -> Unit) {
            Log.i("resposta api commit", commit.toString())
            val callCommit = commentCall.postCommentShortStory(commit)

            callCommit.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    Log.i("resposta api commit", response.message())
                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            })
        }

        fun deleteCommentSS(commentId: Int, shortStoryId: Int, statusCode: (Int) -> Unit) {
            val callDeleteComment = commentCall.deleteCommentShortStory(commentId, shortStoryId)

            callDeleteComment.enqueue(object:
                Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    Log.i("resposta api commit", response.message())
                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //
                }
            }
            )
        }

        fun likeCommentShortStory(likeCommentAnnouncement: LikeComment, statusCode: (Int) -> Unit) {
            val callLikeCommentAnnouncement = commentCall.postLikeShortStoryComment(likeCommentAnnouncement)

            callLikeCommentAnnouncement.enqueue(object:
                Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    Log.i("resposta api commit", response.message())
                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //
                }
            }
            )

        }

        fun dislikeCommentShortStory(commentId: Int, userID: Int, statusCode: (Int) -> Unit){
            val callDislikeCommentAnnouncement = commentCall.dislikeShortStoryComment(commentId, userID)

            callDislikeCommentAnnouncement.enqueue(object:
                Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val status = response.code()

                    Log.i("resposta api commit", response.message())
                    statusCode.invoke(status)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //
                }

            }
            )
        }
    }
}