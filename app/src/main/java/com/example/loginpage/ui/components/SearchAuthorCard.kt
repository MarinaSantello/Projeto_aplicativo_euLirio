package com.example.loginpage.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
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
import com.example.loginpage.models.User
import com.example.loginpage.ui.theme.MontSerratSemiBold
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanExtraLight

@Composable
fun GenerateAuthorCard(
    autor: User,
    navController: NavController,
    usuarioID: Int
    ){

    var followState by remember {
        mutableStateOf(true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
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

            }

            Spacer(modifier = Modifier.height(15.dp))

            val generos = autor.generos

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyRow(
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    items(generos) {
                        Card(
                            modifier = Modifier
                                .padding(start = 3.dp, end = 3.dp),
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


                if(followState){
                    Card(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 3.dp)
                            .clickable { },
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
                }else{
                    Card(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 3.dp)
                            .clickable { },
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
        }
    }

}