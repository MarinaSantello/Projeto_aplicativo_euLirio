package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Genre
import com.example.loginpage.models.Tag
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

    val context = LocalContext.current

    var booksOnClickState by remember {
        mutableStateOf(false)
    }

    var curtasOnClickState by remember {
        mutableStateOf(false)
    }

    var recomendationOnClickState by remember {
        mutableStateOf(false)
    }

    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    Log.i("id usuario", users[0].idUser.toString())

    var foto by remember {
        mutableStateOf("")
    }
    var nome by remember {
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var biografia by remember {
        mutableStateOf("")
    }
    var tags by remember {
        mutableStateOf(listOf<Tag>())
    }
    var genres by remember {
        mutableStateOf(listOf<Genero>())
    }

    CallAPI.getUser(userID){
        foto = it.foto
        nome = it.nome
        userName = it.userName
        biografia = it.biografia
        tags = it.tags
        genres = it.generos
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
                elevation = 0.dp
                ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(24.dp)
//                        .padding(top = 10.dp, start = 20.dp),
//                ) {
//                    Icon(
//                        imageVector = Icons.Rounded.ArrowBackIos,
//                        contentDescription = "flecha para esquerda",
//                        modifier = Modifier
//                            .height(24.dp)
//                            .width(30.dp)
//
//                    )
//                }


                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(300.dp)
                        .padding(start = 41.dp, top = 8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(foto ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
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
                            text = nome,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold

                        )

                        Text(
                            text = "@${userName}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(bottom = 4.dp)

                        )

                        Card(
                            modifier = Modifier
                                .height(12.dp)
                                .width(80.dp)
                                .clickable {
                                    val intent = Intent(context, UpdateActivity::class.java)

                                    context.startActivity(intent)
                                },
                            backgroundColor = colorResource(id = R.color.white),
                            shape = RoundedCornerShape(100.dp)
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
                    text = biografia,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 9.dp)
                )

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
                                text = it.nomeTag,
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

                //Cards para mostrar o Genero selecionado de cada usuario
                LazyRow() {
                    items(genres) {
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
                                text = it.nomeGenero,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Center,
                                color = colorResource(id = R.color.eulirio_yellow_card_background)
                            )

                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TabsPerfil()

//                Row(
//                    modifier = Modifier
//                        .height(25.dp)
//                        .padding(start = 50.dp, end = 50.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//
//                    //Card de Livros
//                    Card(
//                        modifier = Modifier
//                            .height(22.dp)
//                            .width(90.dp)
//                            .clickable {
//                                booksOnClickState = true
//                                curtasOnClickState = false
//                                recomendationOnClickState = false
//                            },
//                        backgroundColor = Color.Transparent,
//                        elevation = 0.dp
//
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//
//                        ) {
//
//                            Icon(
//                                Icons.Outlined.MenuBook,
//                                contentDescription = "icone de livro",
//                                modifier = Modifier
//                                    .height(12.dp)
//                                    .padding(end = 8.dp),
//                                tint = if (booksOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
//                            )
//
//                            Text(
//                                text = "Livros",
//                                fontSize = 8.sp,
//                                fontWeight = FontWeight.W400
//                            )
//
//                        }
//
//                        Spacer(modifier = Modifier.height(5.dp))
//
//                        if (booksOnClickState) {
//                            Card(
//                                modifier = Modifier
//                                    .height(3.dp)
//                                    .fillMaxWidth(),
//                                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
//                                elevation = 0.dp,
//
//
//                                ) {}
//                        }
//
//                    }
//
//                    //Card de Curtas
//                    Card(
//                        modifier = Modifier
//                            .height(22.dp)
//                            .width(90.dp)
//                            .clickable {
//                                curtasOnClickState = true
//                                booksOnClickState = false
//                                recomendationOnClickState = false
//                            },
//                        backgroundColor = Color.Transparent,
//                        elevation = 0.dp
//
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//
//                        ) {
//                            Icon(
//                                Icons.Outlined.FormatAlignCenter,
//                                contentDescription = "icone de livro",
//                                modifier = Modifier
//                                    .height(12.dp)
//                                    .padding(end = 8.dp),
//                                tint = if (curtasOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
//                            )
//
//                            Text(
//                                text = "Curtas",
//                                fontSize = 8.sp,
//                                fontWeight = FontWeight.W400
//                            )
//
//                        }
//
//                        Spacer(modifier = Modifier.height(5.dp))
//
//                        if (curtasOnClickState) {
//                            Card(
//                                modifier = Modifier
//                                    .height(3.dp)
//                                    .fillMaxWidth(),
//                                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
//                                elevation = 0.dp,
//
//
//                                ) {}
//                        }
//
//                    }
//
//                    //Card de Recomendações
//                    Card(
//                        modifier = Modifier
//                            .height(22.dp)
//                            .width(90.dp)
//                            .clickable {
//                                recomendationOnClickState = true
//                                booksOnClickState = false
//                                curtasOnClickState = false
//                            },
//                        backgroundColor = Color.Transparent,
//                        elevation = 0.dp
//
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//
//                        ) {
//                            Icon(
//                                Icons.Outlined.LocalLibrary,
//                                contentDescription = "icone de livro",
//                                modifier = Modifier
//                                    .height(12.dp)
//                                    .padding(end = 8.dp),
//                                tint = if (recomendationOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
//                            )
//
//                            Text(
//                                text = "Recomendações",
//                                fontSize = 8.sp,
//                                fontWeight = FontWeight.W400
//                            )
//
//                        }
//                        Spacer(modifier = Modifier.height(5.dp))
//
//                        if (recomendationOnClickState) {
//                            Card(
//                                modifier = Modifier
//                                    .height(3.dp)
//                                    .fillMaxWidth(),
//                                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
//                                elevation = 0.dp,
//                                ) {}
//                        }
//
//                    }
//
//
//                }


            }


        }


    }
}
@Composable
fun TabsPerfil() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Livros", "Histórias pequenas", "Recomendações")

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(
                                    Icons.Outlined.MenuBook,
                                    contentDescription = "icone de livro",
                                    modifier = Modifier
                                        .height(12.dp)
                                        .padding(end = 8.dp),
                                    //tint = if (booksOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                                )
                            1 -> Icon(
                                    Icons.Outlined.FormatAlignCenter,
                                    contentDescription = "icone de texto",
                                    modifier = Modifier
                                        .height(12.dp)
                                        .padding(end = 8.dp),
                                    //tint = if (curtasOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                                )
                            2 -> Icon(
                                    Icons.Outlined.LocalLibrary,
                                    contentDescription = "icone de pessoa lendo um livro",
                                    modifier = Modifier
                                        .height(12.dp)
                                        .padding(end = 8.dp),
                                    //tint = if (recomendationOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                                )
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> Text(text = "teste livro")
            1 -> Text(text = "teste pequena historia")
            2 -> Text(text = "teste recomendacao")
        }
    }
}


@Composable
fun UserPagePreview() {
    UserPagePreview()
}