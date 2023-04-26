package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Genres
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.shortStory.CallShortStory
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.Anuncios
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.resources.BottomBarScaffold
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.components.TabsUserStories
import com.example.loginpage.ui.theme.*
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

//class SearchViewBooks : ComponentActivity() {
//
//    //    @OptIn(ExperimentalPagerApi::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    SearchBooks(rememberNavController())
//                }
//            }
//        }
//    }
//}

var bottomBarState: MutableState<Boolean> = mutableStateOf(true)
var menuState: MutableState<Boolean> = mutableStateOf(false)
@Composable
fun SearchBooks(navController: NavController) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = 1, idUser = 113)

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
        topBar = { TopBarSearch(userID, scaffoldState, topBarState) },
        bottomBar = { BottomBarScaffold(bottomBarState, navController, users[0].idUser, 2) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (fabState.value) {
                FloatingActionButton() {
                    fabState.value = it
                }
            }
        },
        drawerContent = {
            DrawerDesign(userID, context, scaffoldState, navController)
        },
//
//        drawerGesturesEnabled = true,
    ) {
        ShowBooks(users[0].idUser, it.calculateBottomPadding(), 2, rememberLazyListState(), navController)
    }

    if (!fabState.value) ButtonsPost(navController, context) {
        fabState.value = it
    }
}

@Composable
fun TopBarSearch(
    userID: UserID,
    scaffoldState: ScaffoldState,
    state: MutableState<Boolean>
) {

    var foto by remember {
        mutableStateOf("")
    }

    var searchState by remember {
        mutableStateOf("")
    }



    val RoundedCornerShape = RoundedCornerShape(20.dp)

    CallAPI.getUser(userID.idUser.toLong()) {
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

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(42.dp)
                                .width(280.dp)
                                .padding(end = 10.dp)
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(20.dp)
                                )
                        ) {
                            TextField(
                                value = searchState,
                                onValueChange = { searchState = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .align(Alignment.CenterStart)
                                    .padding(0.dp)
                                    ,
                                placeholder = {
                                    Text(
                                        text = "Buscar por obras e autores",
                                        fontSize = 8.sp
                                    )

                                },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(fontSize = 8.sp)

                            )

                        }

                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Menu burguer",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    menuState.value = true
                                    if (menuState.value) {
                                        bottomBarState.value = false
                                    }


                                }

                        )

                    }

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
                            rememberAsyncImagePainter(
                                foto
                                    ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = "foto de perfil",
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                                .clip(RoundedCornerShape(100.dp))
                        )

                    }
                },
                backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
                elevation = 0.dp
            )
        }
    )
}

@OptIn(ExperimentalUnitApi::class)
//@ExperimentalPagerApi
@Composable
fun TabsFeedSearch(
    userID: Int,
    bottomBarLength: Dp,
    navController: NavController
) {
    var tabIndex by remember { mutableStateOf(0) }
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val tabs = listOf("Livros", "Pequenas Histórias", "Autores")


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
                            Icons.Rounded.Person
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
//        when (tabIndex.currentPage) {
        when (tabIndex) {
            0 -> {
                var announcements by remember {
                    mutableStateOf(listOf<AnnouncementGet>())
                }

                //CallAnnouncementAPI.getAnnouncements {
                CallAnnouncementAPI.getAllAnnouncementsByGenresUser(userID) {
                    announcements = it
                }

                LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(announcements) {
                        AnnouncementCard(it, userID, navController, 1, true, true)
                    }
                }
//                Text(text = "api deu b.o.")
            }
            1 -> {
                var shortStory by remember {
                    mutableStateOf(listOf<ShortStoryGet>())
                }

                //CallShortStoryAPI.getShortStories {
                CallShortStoryAPI.getShortStoriesByGenreUser(userID) {
                    shortStory = it
                }

                LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(shortStory) {
                        ShortStorysCard(it, navController, userID, true, true)
                    }
                }
            }
            2 -> Text(text = "Pequenas Histórias")
        }
    }

    if(menuState.value){
        AnimatedVisibility(
            visible = menuState.value,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it }),
            content = {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    backgroundColor = Color.Black
                ){}
            }
        )
    }




}



