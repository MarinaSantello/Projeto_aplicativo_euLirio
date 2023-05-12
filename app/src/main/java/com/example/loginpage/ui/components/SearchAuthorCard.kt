package com.example.loginpage.ui.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.follow.CallFollowAPI
import com.example.loginpage.API.search.CallSearchaAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.authors
import com.example.loginpage.authorsIsNull
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.Follow
import com.example.loginpage.models.GenreSearch
import com.example.loginpage.models.User
import com.example.loginpage.models.UserFollow
import com.example.loginpage.searchState
import com.example.loginpage.ui.theme.MontSerratSemiBold
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanExtraLight

@Composable
fun GenerateAuthorCard(
    autor: UserFollow,
    navController: NavController,
    usuarioID: Int,
    searchPage: Boolean
){

    var followState by remember {
        mutableStateOf(autor.seguindo)
    }

    val notSameUser by remember {
        mutableStateOf(autor.id != usuarioID)
    }

    Log.i("mesma pessoa?", notSameUser.toString())
    Log.i("id pessoa", autor.id.toString())
    Log.i("id pessoa", usuarioID.toString())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp)
            .clickable {
                navController.navigate("${Routes.User.name}/${autor.id}")
            },
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 8.dp, top = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Row() {
                        Image(
                            rememberAsyncImagePainter(
                                autor.foto
                                    ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = "foto de perfil",
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                                .clip(RoundedCornerShape(100.dp))
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = autor.nome,
                                fontFamily = SpartanBold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "@${autor.userName}",
                                fontFamily = SpartanExtraLight,
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

//                        if (followerState) Card(
//                            modifier = Modifier
//                                .padding(end = 40.dp)
//                                .border(
//                                    .5.dp,
//                                    colorResource(id = R.color.eulirio_black),
//                                    RoundedCornerShape(10.dp)
//                                ),
//                            backgroundColor = Color.Transparent,
//                            shape = RoundedCornerShape(10.dp),
//                            elevation = 0.dp
//                        ) {
//                            Text(
//                                text = "SEGUE VOCÊ",
//                                modifier = Modifier.padding(12.dp, 1.dp),
//                                color = colorResource(id = R.color.eulirio_black),
//                                fontSize = 8.sp,
//                                fontFamily = MontSerratSemiBold,
//                                fontWeight = FontWeight.Light,
//                                textAlign = TextAlign.Center,
//                            )
//                        }
                    }
                }

                if(followState && notSameUser){
                    Card(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 3.dp)
                            .clickable {
                                followState = !followState

                                CallFollowAPI.unfollowUser(usuarioID, autor.id)

                                CallSearchaAPI.searchAuthorByName(
                                    searchState.value, usuarioID
                                ) {
                                    if (it.isNullOrEmpty()) authorsIsNull.value = true
                                    else {
                                        authors.value = it
                                        authorsIsNull.value = false
                                    }
                                }
                            },
                        backgroundColor = Color.Black,
                        border = BorderStroke(
                            1.dp,
                            Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 0.dp
                    ) {
                        Text(
                            text = "SEGUINDO",
                            modifier = Modifier.padding(24.dp, 2.dp),
                            fontSize = 10.sp,
                            fontFamily = MontSerratSemiBold,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            color = Color.White

                        )
                    }
                }else if(!followState && notSameUser){
                    Card(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 3.dp)
                            .clickable {
                                followState = !followState!!

                                val authorFollow = Follow(
                                    idSegue = usuarioID,
                                    idSeguindo = autor.id
                                )

                                CallSearchaAPI.searchAuthorByName(
                                    searchState.value, usuarioID
                                ) {
                                    if (it.isNullOrEmpty()) authorsIsNull.value = true
                                    else {
                                        authors.value = it
                                        authorsIsNull.value = false
                                    }
                                }

                                CallFollowAPI.followUser(authorFollow)
                            },
                        backgroundColor = Color.White,
                        border = BorderStroke(
                            1.dp,
                            Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 0.dp,

                        ) {
                        Text(
                            text = "SEGUIR",
                            modifier = Modifier.padding(24.dp, 2.dp),
                            fontSize = 10.sp,
                            fontFamily = MontSerratSemiBold,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            val generos = autor.generos

            if (searchPage) LazyRow(contentPadding = PaddingValues(bottom = 12.dp)) {
                items(generos) {
                    Card(
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp),
                        backgroundColor = Color.White,
                        border = BorderStroke(
                            1.dp,
                            Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 0.dp
                    ) {
                        Text(
                            text = it.nomeGenero.uppercase(),
                            modifier = Modifier.padding(12.dp, 2.dp),
                            fontSize = 8.sp,
                            fontFamily = MontSerratSemiBold,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

}