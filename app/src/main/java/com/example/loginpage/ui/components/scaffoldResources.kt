package com.example.loginpage.resources

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.LoginPage
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.Tag
import com.example.loginpage.searchState
import com.example.loginpage.ui.theme.Montserrat
import com.example.loginpage.ui.theme.Montserrat2
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@Composable
fun TopBar(
    userID: UserID,
    scaffoldState: ScaffoldState,
    state: MutableState<Boolean>
) {

    var foto by remember {
        mutableStateOf("")
    }

    CallAPI.getUser(userID.idUser.toLong()){
        foto = it.foto
    }

    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = com.example.loginpage.R.drawable.logo_icone_eulirio),
                        contentDescription = "logo aplicativo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 56.dp),
                        alignment = Alignment.Center
//                        modifier = Modifier
//                            .height(90.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {

                        Image(
                            rememberAsyncImagePainter(foto ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                            contentScale = ContentScale.Crop,
                            contentDescription = "foto de perfil",
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                                .clip(RoundedCornerShape(100.dp))
                        )
                    }
                },
                backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_beige_color_background),
                elevation = 0.dp
            )
        }
    )
}


@Composable
fun DrawerDesign(
    userID: UserID,
    context: Context,
    scaffoldState: ScaffoldState,
    navController: NavController
){

    val coroutineScope = rememberCoroutineScope()

    var foto by remember {
        mutableStateOf("")
    }
    var nome by remember {
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var tags by remember {
        mutableStateOf(listOf<Tag>())
    }

    CallAPI.getUser(userID.idUser.toLong()){
        foto = it.foto
        nome = it.nome
        userName = it.userName
        tags = it.tags
    }

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.example.loginpage.R.color.eulirio_yellow_card_background))
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .height(80.dp)
                .padding(start = 40.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("${Routes.User.name}/${userID.idUser}")

                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(foto ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(40.dp))
                )

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 16.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = nome,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "@${userName}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
            }

            LazyRow() {
                items(tags) {
                    Card(
                        modifier = Modifier
                            .height(18.dp)
                            .padding(end = 4.dp)
                            .width(60.dp)
                            .background(Color.Blue),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 0.dp,
                        border = BorderStroke(
                            .5.dp,
                            Color.White
                        )
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
        }

        Card(
            modifier = Modifier
                .height(400.dp)
                .padding(end = 40.dp),
            backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_light_yellow_background),
            shape = RoundedCornerShape(topEnd = 36.dp, bottomEnd = 36.dp),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 36.dp)
                ,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //Card clicavel do perfil do usuario
                Card(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("${Routes.User.name}/${userID.idUser}")

                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        .fillMaxWidth(),
                    backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_light_yellow_background),
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Icone de usuario",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(50.dp),
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_black)
                        )
                        Text(
                            text = "MEU PERFIL",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = com.example.loginpage.R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel de favoritos do usuario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.SavePage.name)
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_light_yellow_background),
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Bookmark,
                            contentDescription = "Icone de usuario",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(50.dp),
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_black)
                        )
                        Text(
                            text = "FAVORITOS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = com.example.loginpage.R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel de lidos do usuario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.Read.name)
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_light_yellow_background),
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = "ICONE DE LIDOS",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(50.dp),
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_black)
                        )
                        Text(
                            text = "LIDOS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = com.example.loginpage.R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel dos e-books do usuario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.PurchasedAnnouncements.name)

                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_light_yellow_background),
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CollectionsBookmark,
                            contentDescription = "Icone de sacola de compras",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(50.dp),
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_black)
                        )
                        Text(
                            text = "ESTANTE",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = com.example.loginpage.R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel para o usuario visualizar suas publicações
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.UserStories.name)

                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_light_yellow_background),
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.EditNote,
                            contentDescription = "Icone de sacola de compras",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(50.dp),
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_black)
                        )
                        Text(
                            text = "MINHAS OBRAS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = com.example.loginpage.R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                Icons.Default.Logout,
                contentDescription = "Icone de logout",
                modifier = Modifier
                    .padding(start = 40.dp, bottom = 40.dp)
                    .height(30.dp)
                    .clickable {

                        val userIDRepository = UserIDrepository(context)
                        val users = userIDRepository.getAll()
                        for (i in users.indices) {
                            val userID2 = UserID(id = users[i].id, idUser = users[i].idUser)
                            userIDRepository.delete(userID2)
                        }

                        auth.signOut() // metodo para deslogar o usuario

                        navController.navigate(Routes.Login.name)
                    }
                ,
                tint = Color.Black
            )

        }

    }
}

@Composable
fun BottomBarScaffold(
    state: MutableState<Boolean>,
    navController: NavController,
    userID: Int,
    activity: Int
) {
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar (
                backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_beige_color_background),
                elevation = 0.dp
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (activity == 1) Icons.Default.Spa else Icons.Outlined.Spa,
                        "",
                        modifier = Modifier
                            .size(if (activity == 1) 32.dp else 28.dp)
                            .clickable {
                                navController.navigate(Routes.Home.name)
                                searchState.value = ""
                            },
                        tint = if (activity == 1) colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border) else colorResource(id = com.example.loginpage.R.color.eulirio_black)
                    )

                    Icon(
                        if (activity == 2) Icons.Default.Search else Icons.Outlined.Search,
                        "",
                        modifier = Modifier
                            .size(if (activity == 2) 32.dp else 28.dp)
                            .clickable {
                                navController.navigate(Routes.SearchPage.name)
                                searchState.value = ""
                            },
                        tint = if (activity == 2) colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border) else colorResource(id = com.example.loginpage.R.color.eulirio_black)
                    )

                    Icon(
                        if (activity == 3) Icons.Default.ShoppingCart else Icons.Outlined.ShoppingCart,
                        "",
                        modifier = Modifier
                            .size(if (activity == 3) 32.dp else 28.dp)
                            .clickable {
                                navController.navigate("${Routes.ShoppingCart.name}/$userID")
                                searchState.value = ""
                            },
                        tint = if (activity == 3) colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border) else colorResource(id = com.example.loginpage.R.color.eulirio_black)
                    )
                }
            }
        }
    )
}
