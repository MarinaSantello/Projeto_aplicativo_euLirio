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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.follow.CallFollowAPI
import com.example.loginpage.API.recommendation.CallRecommendationAPI
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.*
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.components.generateRecommendationCard
import com.example.loginpage.ui.theme.*
//import com.example.euLirio.R

//import com.google.accompanist.pager.ExperimentalPagerApi

//class UserPage : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
//                ) {
//                    UserHomePage()
//                }
//            }
//        }
//    }
//}

//@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserHomePage(
    navController: NavController,
    navUserID: Int
) {

    val context = LocalContext.current

    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    var obras by remember {
        mutableStateOf("")
    }

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
    var followState by remember {
        mutableStateOf(false)
    }
    var followerState by remember {
        mutableStateOf(false)
    }
    var followersState by remember {
        mutableStateOf(0)
    }
    var followingState by remember {
        mutableStateOf(0)
    }

    CallAPI.getNavUser(navUserID.toLong(), userID.idUser.toLong()) {
        foto = it.foto
        nome = it.nome
        userName = it.userName
        biografia = it.biografia
        tags = it.tags
        genres = it.generos
        followState = it.seguindo!!
        followerState = it.teSegue!!
        followersState = it.seguidores!!.qtdSeguidores
        followingState = it.qtdSeguindo!!.qtdeSeguindo
        Log.i("seguindo", it.seguindo.toString())
        obras = it.obras!!.totalObras
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.eulirio_beige_color_background))
            .verticalScroll(rememberScrollState())
    ) {
        //Card de informações do usuario
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 2.dp
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
                                navController.popBackStack()
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
                        painter = rememberAsyncImagePainter(
                            foto
                                ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                        ),
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
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            Text(
                                text = nome,
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.h1

                            )

                            Text(
                                text = "@${userName}",
                                modifier = Modifier.padding(bottom = 4.dp),
                                fontSize = 10.sp,
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.ExtraLight

                            )
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (followerState) Card(
                                modifier = Modifier
                                    .padding(end = 40.dp)
                                    .border(
                                        .5.dp,
                                        colorResource(id = R.color.eulirio_black),
                                        RoundedCornerShape(10.dp)
                                    ),
                                backgroundColor = Color.Transparent,
                                shape = RoundedCornerShape(10.dp),
                                elevation = 0.dp
                            ) {
                                Text(
                                    text = "SEGUE VOCÊ",
                                    modifier = Modifier.padding(12.dp, 1.dp),
                                    color = colorResource(id = R.color.eulirio_black),
                                    fontSize = 8.sp,
                                    fontFamily = MontSerratSemiBold,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Center,
                                )
                            }

                            if (navUserID == userID.idUser) Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.BottomEnd
                            ) {Card(
                                modifier = Modifier
                                    .padding(end = 40.dp)
                                    .clickable {
                                        navController.navigate(Routes.UpdateUser.name)
                                    },
                                backgroundColor = colorResource(id = R.color.eulirio_black),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 0.dp
                            ) {
                                Text(
                                    text = "EDITAR PERFIL",
                                    modifier = Modifier.padding(16.dp, 2.dp),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontFamily = MontSerratSemiBold,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            }
                            else if(followState) Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(end = 40.dp)
                                        .clickable {
                                            followState = !followState

                                            CallFollowAPI.unfollowUser(users[0].idUser, navUserID)

                                            followersState -= 1
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
                            }
                            else Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(end = 40.dp)
                                        .clickable {
                                            followState = !followState

                                            val authorFollow = Follow(
                                                idSegue = users[0].idUser,
                                                idSeguindo = navUserID
                                            )

                                            CallFollowAPI.followUser(authorFollow)

                                            followersState += 1
                                        },
                                    backgroundColor = Color.Transparent,
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


                Row(
                    modifier = Modifier
                        .padding(start = 40.dp, bottom = 20.dp)
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
                            text = obras,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Light,
                            color = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "OBRAS",
                            fontSize = 8.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1,
                            color = colorResource(id = R.color.eulirio_black)
                        )
                    }

                    //Contador de seguindo
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(36.dp)
                            .padding(end = 12.dp)
                            .clickable {
                                navController.navigate("${Routes.FollowsPage.name}/$navUserID/1")
                            }
                    ) {
                        Text(
                            text = followingState.toString(),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Light,
                            color = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "SEGUINDO",
                            fontSize = 8.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1,
                            color = colorResource(id = R.color.eulirio_black)
                        )
                    }

                    //Contador de seguidores
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(36.dp)
                            .clickable {
                                navController.navigate("${Routes.FollowsPage.name}/$navUserID/0")
                            }
                    ) {
                        Text(
                            text = followersState.toString(),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Light,
                            color = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "SEGUIDORES",
                            fontSize = 8.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1,
                            color = colorResource(id = R.color.eulirio_black)
                        )
                    }


                }

            }
        }

        //Coluna para a biografia do autor
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = biografia,
                fontSize = 14.sp,
                fontFamily = QuickSand,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp)
            )

            LazyRow() {
                items(tags) {
                    //Card button com o nome do escritor
                    Card(
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp),
                        backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
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
                modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                color = colorResource(id = R.color.eulirio_purple_text_color_border)
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Cards para mostrar o Genero selecionado de cada usuario
            LazyRow() {
                items(genres) {
                    //Card button com o nome do escritor
                    Card(
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp),
                        backgroundColor = Color.Transparent,
                        border = BorderStroke(
                            1.dp,
                            colorResource(id = R.color.eulirio_purple_text_color_border)
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
                            color = colorResource(id = R.color.eulirio_purple_text_color_border)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            var tabIndex by remember { mutableStateOf(0) }
            val tabs = listOf("Livros", "Pequenas Histórias", "Recomendações")

            Column(modifier = Modifier.fillMaxWidth()) {
                ScrollableTabRow(
//            selectedTabIndex = tabIndex.currentPage,
                    selectedTabIndex = tabIndex,
                    backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
                    modifier = Modifier.height(40.dp),
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                val icons = listOf(
                                    Icons.Outlined.MenuBook,
                                    Icons.Outlined.FormatAlignCenter,
                                    Icons.Outlined.LocalLibrary
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        icons[index],
                                        contentDescription = "icone de livro",
                                        modifier = Modifier
                                            .height(16.dp)
                                            .padding(end = 8.dp),
                                    )
                                    Text(
                                        title,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            selectedContentColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                            unselectedContentColor = Color.Black,
//                    selected = tabIndex.currentPage == index,
                            selected = tabIndex == index,
                            onClick = { tabIndex = index },
//                    onClick = {
//                        coroutineScope.launch {
//                            tabIndex.animateScrollToPage(index)
//                        }
//                    },
                        )
                    }
                }

            }

            when (tabIndex) {
                0 -> {
                    var announcements by remember {
                        mutableStateOf(listOf<AnnouncementGet>())
                    }
                    var announcementIsNull by remember {
                        mutableStateOf(false)
                    }

                    CallAnnouncementAPI.getAnnouncementsByUser(1, navUserID){
                        if(it.isNullOrEmpty()) announcementIsNull == true
                        else announcements = it
                    }

                    if(navUserID == userID.idUser){
                        if (announcementIsNull) Text(text = "Você não possui livros no seu feed por enquanto.")
                        else{
                            for(item in announcements){
                                AnnouncementCard(item, navUserID, navController, 1, true, true)
                            }
                        }
                    }else{
                        if (announcementIsNull) Text(text = "Este usuario não possui livros no feed por enquanto.")
                        else{
                            for(item in announcements){
                                AnnouncementCard(item, navUserID, navController, 1, true, true)
                            }
                        }
                    }



                }
                1 -> {
                    var shortStory by remember {
                        mutableStateOf(listOf<ShortStoryGet>())
                    }

                    var shortStoryIsNull by remember {
                        mutableStateOf(false)
                    }

                    CallShortStoryAPI.getShortStoriesByUser(1, navUserID){
                        if(it.isNullOrEmpty()) shortStoryIsNull == true
                        else shortStory = it


                    }


                    if (shortStoryIsNull){
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text =  if(navUserID == userID.idUser){
                                "Você não possui historias curtas no seu feed por enquanto."
                        }else{
                            "Este usuario não possui historias curtas em seu feed por enquanto"

                        },
                                textAlign = TextAlign.Center,
                                fontFamily = MontSerratBold)
                        }
                    }
                    else{
                        for(item in shortStory){
                            ShortStorysCard(item, navController, navUserID, true, true)
                        }
                    }

                }
                2 -> {
                    var recomendations by remember {
                        mutableStateOf(listOf<Recommendation>())
                    }

                    var recomendationIsNull by remember {
                        mutableStateOf(false)
                    }

                    var userIdRecommendation = 0

                    CallAPI.getUser(navUserID.toLong()){
                        if(it.recomendacoes.isNullOrEmpty()) recomendationIsNull = true
                       else recomendations = it.recomendacoes!!


                    }

                    if (recomendationIsNull)
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text =  if(navUserID == userID.idUser){
                                "Você não possui recomendações no seu feed por enquanto."
                                }else{
                                    "Este usuario não possui recomendações no seu feed por enquanto."

                                },
                                textAlign = TextAlign.Center,
                                fontFamily = MontSerratBold)
                        }
                    }
                    else{
                        for(item in recomendations){
                            generateRecommendationCard(item, navController, navUserID)
                        }
                    }


                }
            }


        }
    }

    @Composable
    fun TabsPerfil() {
        var tabIndex by remember { mutableStateOf(0) }

        val tabs = listOf("Livros", "Histórias pequenas", "Recomendações")

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier.height(20.dp),
            ) {
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

        }
    }


    @Composable
    fun UserPagePreview() {
        UserPagePreview()
    }
}