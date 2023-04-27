package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Genres
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.example.loginpage.API.Search.CallSearchaAPI
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
var searchState: MutableState<String> = mutableStateOf("")
var announcements: MutableState<List<AnnouncementGet>> = mutableStateOf(listOf())
var announcementIsNull: MutableState<Boolean> = mutableStateOf(false)
var shortStories: MutableState<List<ShortStoryGet>> = mutableStateOf(listOf())
var shortStoryIsNull: MutableState<Boolean> = mutableStateOf(false)

@Composable
fun SearchBooks(navController: NavController) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

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
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (fabState.value) {
                FloatingActionButton() {
                    fabState.value = it
                }
            }
        }
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
    state: MutableState<Boolean>,
) {

    var foto by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

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
                                    .5.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(20.dp)
                                )
                        ) {
                            TextField(
                                value = searchState.value,
                                onValueChange = { searchState.value = it },
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
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()

                                        CallSearchaAPI.searchAnnouncementsByName(searchState.value, userID.idUser) {
                                            if (it.isNullOrEmpty()) announcementIsNull.value = true
                                            else {
                                                announcements.value = it
                                                announcementIsNull.value = false
                                            }
                                        }

                                        CallSearchaAPI.searchShortStoriesByName(searchState.value, userID.idUser) {
                                            if (it.isNullOrEmpty()) shortStoryIsNull.value = true
                                            else {
                                                shortStories.value = it
                                                shortStoryIsNull.value = false
                                            }
                                        }
                                    }
                                ),
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
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu burguer",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    menuState.value = !menuState.value
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
                if (announcementIsNull.value) Text(text = "Não existem livros com esse nome.")

                else LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(announcements.value) {
                        AnnouncementCard(it, userID, navController, 1, true, true)
                    }
                }
            }
            1 -> {
                if (shortStoryIsNull.value) Text(text = "Não existem pequenas histórias com esse nome.")

                else LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(shortStories.value) {
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



