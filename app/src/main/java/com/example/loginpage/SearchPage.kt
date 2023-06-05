package com.example.loginpage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginpage.API.search.CallSearchaAPI
import com.example.loginpage.API.genre.GenreCall
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.Genero
import com.example.loginpage.models.GenreSearch
import com.example.loginpage.models.Genres
import com.example.loginpage.resources.BottomBarScaffold
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.GenreCard
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.MontSerratBold
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting2("Android")
                }
            }
        }
    }
}

//var bottomBarState: MutableState<Boolean> = mutableStateOf(true)
//var menuState: MutableState<Boolean> = mutableStateOf(false)
//var searchState: MutableState<String> = mutableStateOf("")
//var announcements: MutableState<List<AnnouncementGet>> = mutableStateOf(listOf())
//var announcementIsNull: MutableState<Boolean> = mutableStateOf(false)

@Composable
fun SearchPage(navController: NavController) {

    val menuState = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = if (users.isNotEmpty()) users[0].id else 0, idUser = if (users.isNotEmpty()) users[0].idUser else 0)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { com.example.loginpage.resources.TopBar(userID, scaffoldState, topBarState) },
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
    ) {it
        Pesquisa(navController, menuState, userID.idUser)
    }

    if (!fabState.value) ButtonsPost(navController, context, 72.dp, fabState) {
        fabState.value = it
    }
}

@Composable
fun Pesquisa(
    navController: NavController,
    menuState: MutableState<Boolean>,
    userID: Int
) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.2f),
            backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            elevation = .5.dp
        ) {
            Row(Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = searchState.value,
                    onValueChange = { searchState.value = it },
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .border(
                            .5.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clip(RoundedCornerShape(24.dp)),
                    placeholder = {
                        Text(
                            text = "Buscar por obras e autores",
                            fontSize = 14.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()

                            val genresChecked = Genres(null)
                            CallSearchaAPI.filterAnnouncements(genresChecked, "", "", userID, "", searchState.value) {
                                if (it.isNullOrEmpty()) announcementIsNull.value = true
                                else {
                                    announcements.value = it
                                    announcementIsNull.value = false
                                }
                            }

                            CallSearchaAPI.filterShortStory(genresChecked, userID, "", searchState.value) {
                                if (it.isNullOrEmpty()) shortStoryIsNull.value = true
                                else {
                                    shortStories.value = it
                                    shortStoryIsNull.value = false
                                }
                            }

                            CallSearchaAPI.searchAuthorByName(
                                searchState.value, userID
                            ) {
                                if (it.isNullOrEmpty()) authorsIsNull.value = true
                                else {
                                    authors.value = it
                                    authorsIsNull.value = false
                                }
                            }

                            navController.navigate(Routes.Search.name)
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
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }

        var genres by remember {
            mutableStateOf(listOf<Genero>())
        }

        val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
        val genreCall = retrofit.create(GenreCall::class.java) // instância do objeto contact
        val callGetGenres01 = genreCall.getAll()

        // Excutar a chamada para o End-point
        callGetGenres01.enqueue(object :
            Callback<List<Genero>> { // enqueue: usado somente quando o objeto retorna um valor
            override fun onResponse(call: Call<List<Genero>>, response: Response<List<Genero>>) {
                genres = response.body()!!

            }

            override fun onFailure(call: Call<List<Genero>>, t: Throwable) {
                Log.i("teste gen", genres.toString())
            }
        })

        Text(
            text = "O que você procura?",
            fontFamily = MontSerratBold,
            modifier = Modifier
                .padding(start = 18.dp, top = 24.dp),
            fontSize = 22.sp,
            color = colorResource(id = R.color.eulirio_purple_text_color_border)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 24.dp,
            )
        ) {
            items(
                items = genres
            ) {gen ->
                GenreCard(gen, colorResource(id = R.color.eulirio_purple_text_color_border), false){
                    if (it) {
                        CallSearchaAPI.filterAnnouncements(
                            Genres(listOf(GenreSearch(gen.nomeGenero))),
                            "",
                            "",
                            userID,
                            "",
                            ""
                        ) {ann ->
                            if (ann.isNullOrEmpty()) announcementIsNull.value = true
                            else {
                                announcements.value = ann
                                announcementIsNull.value = false
                            }

                            menuState.value = !menuState.value
                        }

                        CallSearchaAPI.filterShortStory(
                            Genres(listOf(GenreSearch(gen.nomeGenero))),
                            userID,
                            "",
                            ""
                        ) {ss ->
                            if (ss.isNullOrEmpty()) shortStoryIsNull.value = true
                            else {
                                shortStories.value = ss
                                shortStoryIsNull.value = false
                            }
                        }

                        searchState.value = gen.nomeGenero

                        navController.navigate(Routes.Search.name)
                    }
                }
            }
        }
    }
}