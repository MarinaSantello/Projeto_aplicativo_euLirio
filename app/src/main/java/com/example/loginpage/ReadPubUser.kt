package com.example.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.theme.LoginPageTheme

class ReadPubUser : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
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
fun PubLidas (navController: NavController) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { com.example.loginpage.resources.TopBar(userID, scaffoldState, topBarState) },
        drawerContent = {
            DrawerDesign(userID, context, scaffoldState, navController)
        },
    ) {
        ShowBooks(users[0].idUser, it.calculateBottomPadding(), 6, topBarState, bottomBarState, navController)
    }
}

@Composable
fun TabsUserPubLidas(
    userID: Int,
    bottomBarLength: Dp,
    navController: NavController
) {
    var tabIndex by remember { mutableStateOf(0) }
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

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
                        val icons = listOf(Icons.Outlined.MenuBook, Icons.Outlined.FormatAlignCenter, Icons.Outlined.LocalLibrary)

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
                var announcementIsNull by remember {
                    mutableStateOf(false)
                }

                CallAnnouncementAPI.getUserReadedAnnouncements(userID) {
                    if (it.isNullOrEmpty()) announcementIsNull = true
                    else announcements = it
                }

                if (announcementIsNull) Text(text = "Você não possui livros lidos.")

                else LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(announcements) {
                        AnnouncementCard(it, userID, navController, 1, true, false)
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

                //CallShortStoryAPI.getShortStories {
                CallShortStoryAPI.getUserReadedShortStories(userID) {
                    if (it.isNullOrEmpty()) shortStoryIsNull = true
                    else shortStory = it
                }

                if (shortStoryIsNull) Text(text = "Você não possui pequenas histórias lidas.")

                else LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(shortStory) {
                        ShortStorysCard(it, navController, userID, true, false)
                    }
                }
            }
            2 -> Text(text = "Pequenas Histórias")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview2() {
//    LoginPageTheme {
//        Greeting2("Android")
//    }
//}