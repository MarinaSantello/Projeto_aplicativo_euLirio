package com.example.loginpage

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.resources.TopBar
import com.example.loginpage.ui.theme.*

class ShortStories : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun SStories(name: String) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!fabState.value)
                            fabState.value = !fabState.value
                    }
                )
            },
        scaffoldState = scaffoldState,
        topBar = { TopBar(userID, scaffoldState, topBarState) },
        drawerContent = {
            DrawerDesign(userID, context, scaffoldState)
        },
//
//        drawerGesturesEnabled = true,
    ) {
        ShowBooks(users[0].idUser, it.calculateBottomPadding(), 3)
    }
}

@Composable
fun ShowStories(
    userAuthor: Boolean,
    context: Context) {

    var likeState by remember {
        mutableStateOf(false)
    }

    var saveState by remember {
        mutableStateOf(false)
    }
    var viewState by remember {
        mutableStateOf(false)
    }

    var postState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Divider(
            thickness = .5.dp,
            color = Color.White
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(168.dp),
            shape = RoundedCornerShape(
                bottomEnd = if (userAuthor) 40.dp else 0.dp,
                bottomStart = if (userAuthor) 40.dp else 0.dp
            ),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 0.dp
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(28.dp, 12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                    contentDescription = "capa do livro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(98.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Column() {

                        Text(
                            text = "Lorem Ipsum",
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Light,
                            fontFamily = SpartanBold,
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.eulirio_black)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        val generos = listOf<String>("genero 1", "genero 2", "genero 4")

                        LazyRow() {
                            items(generos) {
                                Card(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp),
                                    backgroundColor = Color(0xFF1B0C36),
                                    shape = RoundedCornerShape(100.dp),
                                ) {
                                    Text(
                                        text = it,//.nome.uppercase(),
                                        fontSize = 10.sp,
                                        fontFamily = MontSerratSemiBold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(12.dp, 1.dp),
                                        color = Color.White
                                    )

                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        //Sistema de avaliação
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Outlined.StarOutline,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(text = "(4,5)")

                        }
                    }

                    Row() {
                        //Coluna de curtir
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário curtiu a publicação
                                if (likeState) {
                                    Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = "icone de curtir",
                                        modifier = Modifier
                                            .clickable { likeState = !likeState },
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                    )
                                } else Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = "icone de curtir",
                                    modifier = Modifier
                                        .clickable { likeState = !likeState },
                                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                )

                                Text(
                                    text = "curtidas",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = "570",
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        //Coluna de favoritar
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário favoritou a publicação
                                if (saveState) {
                                    Icon(
                                        Icons.Outlined.Bookmark,
                                        contentDescription = "icone de salvar",
                                        modifier = Modifier
                                            .clickable { saveState = !saveState },
                                        tint = Color.White
                                    )
                                } else Icon(
                                    Icons.Outlined.BookmarkAdd,
                                    contentDescription = "icone de salvar",
                                    modifier = Modifier
                                        .clickable { saveState = !saveState },
                                    tint = Color.White
                                )

                                Text(
                                    text = "favoritos",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = "182",
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        //Coluna de marcar como lido
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário marcou como lido a publicação
                                if (viewState) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "icone de salvar",
                                        modifier = Modifier
                                            .clickable { viewState = !viewState },
                                        tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                    )
                                } else Icon(
                                    Icons.Outlined.CheckCircle,
                                    contentDescription = "icone de salvar",
                                    modifier = Modifier
                                        .clickable { viewState = !viewState },
                                    tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                )

                                Text(
                                    text = "lidos",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = "4,1k",
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.height(1000.dp)) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                    contentDescription = "foto da pessoa",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp)
                        .width(30.dp)
                        .clip(
                            RoundedCornerShape(15.dp)
                        )
                )

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Noah Sebastian",
                        fontSize = 16.sp,
                        fontFamily = SpartanMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "@n.sebastian",
                        fontSize = 14.sp,
                        fontFamily = Spartan
                    )
                }
                Icon(
                    Icons.Outlined.CalendarMonth,
                    contentDescription = "icone de publicacoes",
                    modifier = Modifier
                        .clickable {postState = !postState},
                    tint = Color.Black
                )

                Text(
                    text = "publicação",
                    fontSize = 10.sp,
                    fontFamily = QuickSand,
                    fontWeight = FontWeight.W500,

                    )
                Text(
                    text = "12 Jun.2022",
                    fontSize = 12.sp,
                    fontFamily = MontSerratSemiBold,
                    fontWeight = FontWeight.Bold
                )
            }

            //sinopse
            Column() {
//            Text(
//                text = stringResource(id = R.string.review))
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Row() {
                    Text(
                        text = "Classificação indicativa:",
                        fontFamily = Spartan,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "Livre",
                        fontFamily = SpartanBold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    LoginPageTheme {

    }
}