package com.example.loginpage.ui.components

import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.comment.CallCommentAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.R
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.Commit
import com.example.loginpage.models.CommitSS
import com.example.loginpage.models.LikeComment
import com.example.loginpage.models.User
import com.example.loginpage.statusState
import com.example.loginpage.ui.theme.QuickSand
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanRegular
import org.w3c.dom.Comment
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun CommentCard(
    comment: Commit,
    navController: NavController,
    userId: Int,
    announcementId: Int,
    delete: (Boolean) -> Unit
){

    val idUsuario = comment.userID.toLong();
    var userPicture by remember {
        mutableStateOf("")
    }


    var selectedItem by remember {
        mutableStateOf(0)
    }

    var expandedTopBar by remember {
        mutableStateOf(false)
    }

    var expandedTopBarState by remember {
        mutableStateOf(false)
    }

    var stringTopBarState by remember {
        mutableStateOf(listOf(""))
    }


    var userName by remember {
        mutableStateOf("")
    }
    var visibilitySpoiler by remember {
        mutableStateOf(comment.spoiler)
    }

    CallAPI.getUser(idUsuario){
        userPicture = it.foto
        userName = it.userName
    }

    val filledStars = floor(comment.avaliacao.toDouble()).toInt()
    val unfilledStars = (5 - ceil(comment.avaliacao.toDouble())).toInt()
    val halfStar = !(comment.avaliacao.toDouble().rem(1).equals(0.0))

    val mouths = listOf(
        "Jan.",
        "Fev.",
        "Mar.",
        "Abr.",
        "Mai.",
        "Jun.",
        "Jul.",
        "Ago.",
        "Set.",
        "Out.",
        "Nov.",
        "Dez."
    )
    val date = comment.dataPublicado!!.split("T")[0].split("-")
    val mouth = mouths[(date[1].toInt() - 1)]

    //Card de comentarios
    var likeStateComment by remember {
        mutableStateOf(comment.curtido)
    }

    var quantidadeLikesComment by remember {
        mutableStateOf(comment.curtidas!!.qtdeCurtidas.toString())
    }



    Log.i("spoiler", visibilitySpoiler.toString())

    if(visibilitySpoiler == "1") Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(120.dp)
            .clickable { visibilitySpoiler = "0" },
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Contem Spoiler",
                fontFamily = SpartanBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))

            Icon(
                imageVector = Icons.Outlined.VisibilityOff,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = colorResource(R.color.eulirio_purple_text_color_border)
            )


        }
    }

    else Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(120.dp)
            .padding(0.dp, 10.dp),
        elevation = 0.dp
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.clickable { navController.navigate("${Routes.User.name}/${comment.userID}") },
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            userPicture
                                ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = "",
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .border(.5.dp, Color.White, RoundedCornerShape(100.dp))
                    )
                    Column(
                        modifier = Modifier.padding(start = 12.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Row() {
                            repeat(filledStars) {
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp),
                                    tint = colorResource(R.color.eulirio_purple_text_color_border)
                                )
                            }

                            if (halfStar) {
                                Icon(
                                    imageVector = Icons.Outlined.StarHalf,
                                    contentDescription = null,
                                    tint = colorResource(R.color.eulirio_purple_text_color_border)
                                )
                            }
                            repeat(unfilledStars) {
                                Icon(
                                    imageVector = Icons.Outlined.StarOutline,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp),
                                    tint = colorResource(R.color.eulirio_purple_text_color_border)
                                )
                            }
                        }
                        Text(
                            text = "@${userName}",
                            fontFamily = SpartanRegular,
                            fontSize = 10.sp

                        )

                    }
                }

                if (!expandedTopBarState && comment.userID == userId) {
                    Card(
                        modifier = Modifier
                            .height(25.dp)
                            .clickable {
                                expandedTopBar = true
                                expandedTopBarState = true
                            }
                            .width(25.dp),
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,

                        ) {
                        Icon(
                            Icons.Rounded.MoreVert, contentDescription = "",
                            modifier = Modifier.size(2.dp),
                            tint = Color.Black
                        )
                    }
                }
            }

//            if (announcementAuthorId == userId) {
//                stringStateAuthorAnnouncementView = true
//                stringTopBarState = listOf("Apagar comentário", "Denunciar comentário")
//            } else if (comment.userID == userId) {
//                stringStateAuthorCommentView = true
//                stringTopBarState = listOf("Apagar comentário")
//            } else {
//                stringStateCommentView = true
//                stringTopBarState = listOf("Denunciar Comentário")
//            }

            stringTopBarState = listOf("Apagar comentário")
            //Validação para excluir o comentário
            if (comment.userID == userId) {
                DropdownMenu(
                    expanded = expandedTopBar,
                    onDismissRequest = {
                        expandedTopBar = false
                        expandedTopBarState = false
                    },
                    modifier = Modifier.fillMaxWidth(.85f)
                ) {
                    stringTopBarState.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = index
                                expandedTopBar = false
                                expandedTopBarState = false

                                if (index == 0) {
                                    CallCommentAPI.deleteComment(comment.id!!, announcementId) {
                                        if (it == 200) delete.invoke(true)
                                    }
                                }

                            }
                        ) {
                            Text(text = item)
                        }
                    }
                }
            }
//            else if (stringStateAuthorCommentView) {
//                DropdownMenu(
//                    expanded = expandedTopBar,
//                    onDismissRequest = {
//                        expandedTopBar = false
//                        expandedTopBarState = false
//                    },
//                    modifier = Modifier.fillMaxWidth(.85f)
//                ) {
//                    stringTopBarState.forEachIndexed { index, item ->
//                        DropdownMenuItem(
//                            onClick = {
//                                selectedItem = index
//                                expandedTopBar = false
//                                expandedTopBarState = false
//
//                                if (index == 0) {
//                                    CallCommentAPI.deleteComment(comment.id!!, announcementId)
//
//                                }
//
//                            }
//                        ) {
//                            Text(text = item)
//                        }
//                    }
//                }
//            } else {
//                DropdownMenu(
//                    expanded = expandedTopBar,
//                    onDismissRequest = {
//                        expandedTopBar = false
//                        expandedTopBarState = false
//                    },
//                    modifier = Modifier.fillMaxWidth(.85f)
//                ) {
//                    stringTopBarState.forEachIndexed { index, item ->
//                        DropdownMenuItem(
//                            onClick = {
//                                selectedItem = index
//                                expandedTopBar = false
//                                expandedTopBarState = false
//                            }
//                        ) {
//                            Text(text = item)
//                        }
//                    }
//
//                }
//
//            }


                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = comment.titulo,
                        fontFamily = SpartanBold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = comment.resenha,
                        fontFamily = QuickSand,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .height(25.dp)
                        .fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .height(20.dp)
                            .width(80.dp)
                            .clickable {
                                likeStateComment = !likeStateComment!!

                                var likeComment = LikeComment(
                                    idComentario = comment.id!!,
                                    idUsuario = userId
                                )

                                if (likeStateComment == true) {
                                    CallCommentAPI.likeCommentAnnouncement(likeComment)
                                    var changeQuantidadeLikesComment = quantidadeLikesComment.toInt() + 1
                                    quantidadeLikesComment = changeQuantidadeLikesComment.toString()

                                } else {
                                    CallCommentAPI.dislikeCommentAnnouncement(comment.id!!, userId)
                                    var changeQuantidadeLikesComment = quantidadeLikesComment.toInt() - 1
                                    quantidadeLikesComment = changeQuantidadeLikesComment.toString()
                                }

                            },
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(100.dp),
                        border = if (likeStateComment == true) {
                            BorderStroke(1.dp, Color.Red)
                        } else {
                            BorderStroke(1.dp, Color.Black)
                        }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (likeStateComment == true) {
                                Icon(
                                    Icons.Rounded.Favorite,
                                    contentDescription = "",
                                    modifier = Modifier.size(15.dp),
                                    tint = Color.Red
                                )
                            } else {
                                Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = "",
                                    modifier = Modifier.size(15.dp),
                                    tint = Color.Black
                                )
                            }


                            Text(
                                text = quantidadeLikesComment,
                                fontFamily = SpartanRegular,
                                modifier = Modifier.padding(start = 6.dp)
                            )

                        }

                    }

                    Text(
                        text = "${date[2]} $mouth ${date[0]}",
                        fontFamily = SpartanRegular,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }