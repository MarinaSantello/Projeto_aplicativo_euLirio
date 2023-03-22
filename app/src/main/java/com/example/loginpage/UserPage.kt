package com.example.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.euLirio.R
import com.example.loginpage.ui.theme.LoginPageTheme

class UserPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    UserHomePage()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserHomePage() {


    var BooksOnClickState by remember {
        mutableStateOf(false)
    }

    var CurtasOnClickState by remember {
        mutableStateOf(false)
    }

    var RecomendationOnClickState by remember {
        mutableStateOf(false)
    }



    Card(
        backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()

    ) {


        //Card de informações do usuario
        Column(
            modifier = Modifier
                .fillMaxSize(),

            ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp)
                    .shadow(55.dp),
                shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp),
                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),

                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(top = 10.dp, start = 20.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIos,
                        contentDescription = "flecha para esquerda",
                        modifier = Modifier
                            .height(24.dp)
                            .width(30.dp)

                    )
                }


                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(300.dp)
                        .padding(start = 41.dp, top = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_icon_user_eulirio_example_background),
                        contentDescription = "",
                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .clip(RoundedCornerShape(100.dp))


                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, bottom = 10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Noah Sebastian",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold

                        )

                        Text(
                            text = "@n.sebastian",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(bottom = 4.dp)

                        )

                        Card(
                            modifier = Modifier
                                .height(12.dp)
                                .width(80.dp),
                            backgroundColor = colorResource(id = R.color.white),
                            shape = RoundedCornerShape(100.dp),

                            ) {
                            Text(
                                text = "EDITAR PERFIL",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Center,

                                )
                        }


                    }

                }


                Row(

                    modifier = Modifier
                        .padding(start = 41.dp)
                        .width(169.dp)
                        .height(20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom
                ) {

                    //Contador de obras
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .height(50.dp)
                            .width(33.dp)
                    ) {
                        Text(
                            text = "182",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold


                        )
                        Text(
                            text = "OBRAS",
                            fontSize = 8.sp,
                            modifier = Modifier.padding(bottom = 5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light
                        )

                    }

                    //Contador de seguindo
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(50.dp)
                            .width(52.dp)
                    ) {
                        Text(
                            text = "570",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold

                        )
                        Text(
                            text = "SEGUINDO",
                            fontSize = 8.sp,
                            modifier = Modifier.padding(bottom = 5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light
                        )

                    }

                    //Contador de seguidores
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(50.dp)
                            .width(52.dp)
                    ) {
                        Text(
                            text = "570",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold

                        )
                        Text(
                            text = "SEGUIDORES",
                            fontSize = 8.sp,
                            modifier = Modifier.padding(bottom = 5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light
                        )

                    }


                }

            }

            //Coluna para a biografia do autor
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier


            ) {
                Text(
                    text = "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 9.dp)
                )


                //Cards da tag de ESCRITOR e LEITOR
                val tags = listOf<String>("ESCRITOR", "AUTOR")

                LazyRow() {
                    items(tags) {
                        //Card button com o nome do escritor
                        Card(
                            modifier = Modifier
//                            .padding(end = 6.dp)
                                .height(16.dp)
                                .padding(start = 5.dp, end = 5.dp)
                                .width(90.dp),
                            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                            shape = RoundedCornerShape(100.dp),
                        ) {
                            Text(
                                text = it,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                color = Color.White


                            )

                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = colorResource(id = R.color.eulirio_yellow_card_background)
                )

                Spacer(modifier = Modifier.height(10.dp))

                val generos = listOf<String>("TERROR", "DRAMA", "ROMANCE")


                //Cards para mostrar o Genero selecionado de cada usuario
                LazyRow() {
                    items(generos) {
                        //Card button com o nome do escritor
                        Card(
                            modifier = Modifier
//                            .padding(end = 6.dp)
                                .height(14.dp)
                                .padding(start = 5.dp, end = 5.dp)
                                .width(90.dp),
                            backgroundColor = colorResource(id = R.color.white),
                            border = BorderStroke(
                                1.dp,
                                colorResource(id = R.color.eulirio_yellow_card_background)
                            ),
                            shape = RoundedCornerShape(100.dp),
                        ) {
                            Text(
                                text = it,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Center,
                                color = colorResource(id = R.color.eulirio_yellow_card_background)


                            )

                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .height(25.dp)
                        .padding(start = 50.dp, end = 50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    //Card de Livros
                    Card(
                        modifier = Modifier
                            .height(22.dp)
                            .width(90.dp)
                            .clickable {
                                BooksOnClickState = true
                                CurtasOnClickState = false
                                RecomendationOnClickState = false
                            },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {

                            Icon(
                                Icons.Outlined.MenuBook,
                                contentDescription = "icone de livro",
                                modifier = Modifier
                                    .height(12.dp)
                                    .padding(end = 8.dp),
                                tint = if (BooksOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                            )

                            Text(
                                text = "Livros",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.W400
                            )

                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        if (BooksOnClickState) {
                            Card(
                                modifier = Modifier
                                    .height(3.dp)
                                    .fillMaxWidth(),
                                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                                elevation = 0.dp,


                                ) {}
                        }

                    }

                    //Card de Curtas
                    Card(
                        modifier = Modifier
                            .height(22.dp)
                            .width(90.dp)
                            .clickable {
                                CurtasOnClickState = true
                                BooksOnClickState = false
                                RecomendationOnClickState = false
                            },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Icon(
                                Icons.Outlined.FormatAlignCenter,
                                contentDescription = "icone de livro",
                                modifier = Modifier
                                    .height(12.dp)
                                    .padding(end = 8.dp),
                                tint = if (CurtasOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                            )

                            Text(
                                text = "Curtas",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.W400
                            )

                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        if (CurtasOnClickState) {
                            Card(
                                modifier = Modifier
                                    .height(3.dp)
                                    .fillMaxWidth(),
                                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                                elevation = 0.dp,


                                ) {}
                        }

                    }

                    //Card de Recomendações
                    Card(
                        modifier = Modifier
                            .height(22.dp)
                            .width(90.dp)
                            .clickable {
                                RecomendationOnClickState = true
                                BooksOnClickState = false
                                CurtasOnClickState = false
                                       },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Icon(
                                Icons.Outlined.LocalLibrary,
                                contentDescription = "icone de livro",
                                modifier = Modifier
                                    .height(12.dp)
                                    .padding(end = 8.dp),
                                tint = if (RecomendationOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                            )

                            Text(
                                text = "Recomendações",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.W400
                            )

                        }
                        Spacer(modifier = Modifier.height(5.dp))

                        if (RecomendationOnClickState) {
                            Card(
                                modifier = Modifier
                                    .height(3.dp)
                                    .fillMaxWidth(),
                                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                                elevation = 0.dp,


                                ) {}
                        }

                    }


                }


            }


        }


    }
}


@Composable
fun UserPagePreview() {
    UserPagePreview()
}