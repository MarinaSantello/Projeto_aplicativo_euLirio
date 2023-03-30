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
import androidx.compose.material.icons.filled.ArrowBackIos
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
import androidx.compose.ui.layout.ContentScale
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
import com.example.loginpage.ui.theme.Montserrat
import com.example.loginpage.ui.theme.Montserrat2
import com.example.loginpage.ui.theme.QuickSand
//import com.google.accompanist.pager.ExperimentalPagerApi

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

//@OptIn(ExperimentalPagerApi::class)
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
//            .height(300.dp)
            .fillMaxWidth()

    ) {


        //Card de informações do usuario
        Column(
            modifier = Modifier
                .fillMaxSize()
            ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.25f),
                shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp),
                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                elevation = 5.dp
                ) {
                Column(
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(top = 10.dp, start = 20.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "flecha para esquerda",
                            modifier = Modifier
                                .height(24.dp)
                                .width(30.dp)
                                .clickable {
                                    val intent = Intent(context, Home::class.java)
                                    context.startActivity(intent)
                                }
                        )
                    }

                    Divider(
                        thickness = .5.dp,
                        color = Color.White
                    )

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(80.dp)
                            .padding(start = 40.dp, top = 12.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(foto ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .border(.5.dp, Color.White, RoundedCornerShape(100.dp))
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 12.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = nome,
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.h1

                            )

                            Text(
                                text = "@${userName}",
                                modifier = Modifier.padding(bottom = 4.dp),
                                fontSize = 10.sp,
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.ExtraLight

                            )

                            Card(
                                modifier = Modifier
                                    .padding(start = 148.dp)
                                    .clickable {
                                        val intent = Intent(context, UpdateActivity::class.java)

                                        context.startActivity(intent)
                                    },
                                backgroundColor = colorResource(id = R.color.white),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 0.dp
                            ) {
                                Text(
                                    text = "EDITAR PERFIL",
                                    modifier = Modifier.padding(12.dp, 2.dp),
                                    fontSize = 8.sp,
                                    fontFamily = Montserrat2,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Center,
                                )
                            }


                        }

                    }


                    Row(

                        modifier = Modifier
                            .padding(start = 40.dp)
                            .heightIn(20.dp),
                        horizontalArrangement = Arrangement.Start,
//                        verticalAlignment = Alignment.Bottom
                    ) {

                        //Contador de obras
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(36.dp)
                                .padding(end = 12.dp)
                        ) {
                            Text(
                                text = "182",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h2,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                            Text(
                                text = "OBRAS",
                                fontSize = 8.sp,
                                modifier = Modifier.padding(bottom = 4.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White
                            )
                        }

                        //Contador de seguindo
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(36.dp)
                                .padding(end = 12.dp)
                        ) {
                            Text(
                                text = "570",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h2,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                            Text(
                                text = "SEGUINDO",
                                fontSize = 8.sp,
                                modifier = Modifier.padding(bottom = 4.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White
                            )
                        }

                        //Contador de seguidores
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(36.dp)
                        ) {
                            Text(
                                text = "4,1k",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h2,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                            Text(
                                text = "SEGUIDORES",
                                fontSize = 8.sp,
                                modifier = Modifier.padding(bottom = 4.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White
                            )
                        }


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
                    fontSize = 13.sp,
                    fontFamily = QuickSand,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 9.dp)
                )

                LazyRow() {
                    items(tags) {
                        //Card button com o nome do escritor
                        Card(
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp),
                            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                            shape = RoundedCornerShape(20.dp),
                            elevation = 0.dp
                        ) {
                            Text(
                                text = it.nomeTag.uppercase(),
                                modifier = Modifier.padding(24.dp, 2.dp),
                                fontSize = 12.sp,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.ExtraLight,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 4.dp),
                    color = colorResource(id = R.color.eulirio_yellow_card_background)
                )

                Spacer(modifier = Modifier.height(8.dp))

                //Cards para mostrar o Genero selecionado de cada usuario
                LazyRow() {
                    items(genres) {
                        //Card button com o nome do escritor
                        Card(
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp),
                            backgroundColor = colorResource(id = R.color.white),
                            border = BorderStroke(
                                1.dp,
                                colorResource(id = R.color.eulirio_yellow_card_background)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = 0.dp
                        ) {
                            Text(
                                text = it.nomeGenero.uppercase(),
                                modifier = Modifier.padding(24.dp, 2.dp),
                                fontSize = 10.sp,
                                fontFamily = Montserrat2,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                color = colorResource(id = R.color.eulirio_yellow_card_background)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TabsFiltro()

            }


        }


    }
}
@Composable
fun TabsPerfil() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Livros", "Histórias pequenas", "Recomendações")

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(selectedTabIndex = tabIndex,
            modifier = Modifier.height(20.dp),) {
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