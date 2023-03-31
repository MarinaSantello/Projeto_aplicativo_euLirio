package com.example.loginpage

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.MontSerratSemiBold
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanMedium
import kotlinx.coroutines.launch

//class Ebook : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    EbookView()
//                }
//            }
//        }
//    }
//}

@Composable
fun EbookView(idAnnouncement: Int) {
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
        topBar = { TopBarEbook(scaffoldState, topBarState, context, false) },
        bottomBar = { BottomBarEbook(bottomBarState, true, context) },
    ) {
        ShowEbook(idAnnouncement, it.calculateBottomPadding())
    }
}

@Composable
fun ShowEbook(idAnnouncement: Int, bottomBarLength: Dp) {
    CallAnnouncementAPI.getAnnouncement(idAnnouncement) {
        val announcementGet = it
    }


    //Card de informações do usuario
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
            shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 0.dp
        ){
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
                                    .padding(start = 4.dp, end = 4.dp)
                                ,
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

                    Spacer(modifier = Modifier.width(12.dp))

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
                        
                        Text(text = "4,5")

                    }
                    
                }
            }
        }
        
        Column(Modifier.height(1000.dp)) {
            Text(text = "teste")
            
        }
    }


}

@Composable
fun BottomBarEbook(bottomBarState: MutableState<Boolean>, userAuthor: Boolean, context: Context) {

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            if (userAuthor) {
                BottomAppBar(
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "oi", Toast.LENGTH_SHORT).show()
                    },
                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Text(
                        text = stringResource(R.string.ebook_edit).uppercase(),
                        modifier = Modifier
                            .fillMaxWidth(),
//                                .padding(end = 44.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        style = MaterialTheme.typography.h2
                    )
                }
            }

            else {
                BottomAppBar(
                    contentPadding = PaddingValues(0.dp),
                    elevation = 0.dp
//                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Row(Modifier.fillMaxSize()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .fillMaxHeight()
                                .clickable {
                                    Toast
                                        .makeText(context, "oi", Toast.LENGTH_SHORT)
                                        .show()
                                },
                            shape = RoundedCornerShape(0.dp),
                            backgroundColor = Color.White,
                            elevation = 0.dp
                        ) {
                            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.ebook_bag).uppercase(),
                                    color = colorResource(id = R.color.eulirio_yellow_card_background),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    style = MaterialTheme.typography.h2
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    Toast
                                        .makeText(context, "oi", Toast.LENGTH_SHORT)
                                        .show()
                                },
                            shape = RoundedCornerShape(0.dp),
                            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                            elevation = 0.dp
                        ) {
                            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.ebook_buy).uppercase(),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    style = MaterialTheme.typography.h2
                                )
                            }
                        }
                    }
                }
            }
        }
    )

}

@Composable
fun TopBarEbook(scaffoldState: ScaffoldState, topBarState: MutableState<Boolean>, context: Context,  userAuthor: Boolean) {

    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = topBarState.value,
        modifier = Modifier.background(Color.Red),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.title_ebook).uppercase(),
                            modifier = Modifier
                                .fillMaxWidth(.8f),
//                                .padding(end = 44.dp),
                            color = colorResource(id = R.color.eulirio_black),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2
                        )

                        Icon(
                            Icons.Rounded.MoreVert,
                            contentDescription = "botao de menu",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .fillMaxHeight()
                                .width(32.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .clickable { },
                            tint = if (userAuthor) Color.Transparent else colorResource(id = R.color.eulirio_black)
                        )

                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val intent = Intent(context, Home::class.java)
                                context.startActivity(intent)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Rounded.ChevronLeft,
                            contentDescription = "botao para voltar",
                            modifier = Modifier
                                .fillMaxSize(.8f)
                                .padding(start = 12.dp)
                                .clip(RoundedCornerShape(100.dp)),
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                    }
                },
                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                elevation = 0.dp
            )
        }
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    LoginPageTheme {
        EbookView(1)
    }
}