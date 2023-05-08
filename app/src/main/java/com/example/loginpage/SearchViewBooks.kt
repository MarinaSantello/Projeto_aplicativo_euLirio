package com.example.loginpage

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.search.CallSearchaAPI
import com.example.loginpage.API.genre.CallGenreAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.*
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.GenerateAuthorCard
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.theme.*
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

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
var authors: MutableState<List<UserFollow>> = mutableStateOf(listOf())
var authorsIsNull: MutableState<Boolean> = mutableStateOf(false)

@Composable
fun SearchBooks(navController: NavController) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }
    val fabVisibility = remember { mutableStateOf(true) }
    val tabIndex = remember { mutableStateOf(0) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBarSearch(userID, scaffoldState, topBarState, fabVisibility, tabIndex) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (fabState.value && fabVisibility.value) {
                FloatingActionButton() {
                    fabState.value = it
                }
            }
        }
    ) { padding ->
        ShowBooks(
            users[0].idUser,
            padding.calculateBottomPadding(),
            2,
            topBarState,
            bottomBarState,
            navController,
            tabIndex
        )
    }

    if (menuState.value && tabIndex.value != 2) Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        var generos by remember {
            mutableStateOf(listOf<Genero>())
        }
        var genres by remember {
            mutableStateOf(listOf<GenreSearch>())
        }
        CallGenreAPI.callGetGenre {
            generos = it
        }
        val rows = (generos.size + 2) / 3

        var selectedValue by rememberSaveable { mutableStateOf("") }

        var minValue by remember { mutableStateOf("") }
        var maxValue by remember { mutableStateOf("") }

        val focusManager = LocalFocusManager.current
        BackHandler(menuState.value,
            onBack = {
                menuState.value = !menuState.value
            })

        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0x80000000))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            menuState.value = !menuState.value
                        }
                    )
                }
        )
        AnimatedVisibility(
            visible = menuState.value,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.6f),
                backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "FILTROS",
                            fontSize = 28.sp,
                            fontFamily = MontSerratBold

                        )
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 28.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Gêneros",
                            fontSize = 16.sp,
                            fontFamily = MontSerratBold

                        )
                    }

                    repeat(rows) { rowIndex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            (rowIndex * 3..(rowIndex * 3) + 2).forEachIndexed { colIndex, itemIndex ->
                                if (itemIndex < generos.size) {
                                    var checkGenre by rememberSaveable() {
                                        mutableStateOf(false)
                                    }

                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {
                                                checkGenre = !checkGenre

                                                if (checkGenre) genres += GenreSearch (generos[itemIndex].nomeGenero)
                                                else genres -= GenreSearch (generos[itemIndex].nomeGenero)
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Checkbox(
                                            checked = checkGenre,
                                            onCheckedChange = {
                                                checkGenre = it

                                                if (checkGenre) genres += GenreSearch (generos[itemIndex].nomeGenero)
                                                else genres -= GenreSearch (generos[itemIndex].nomeGenero)
                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                                                uncheckedColor = colorResource(id = R.color.eulirio_black)
                                            )

                                        )
                                        Text(
                                            text = generos[itemIndex].nomeGenero.uppercase(),
                                            fontWeight = FontWeight.W500,
                                            fontSize = 12.sp

                                        )
                                    }

                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

                    if (tabIndex.value == 0) {
                        Divider(
                            thickness = 1.dp,
                            color = colorResource(id = R.color.eulirio_purple_text_color_border)
                        )

                        Column(
                            modifier = Modifier
                                .padding(top = 14.dp, bottom = 12.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "Ordem",
                                    fontSize = 16.sp,
                                    fontFamily = MontSerratBold

                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            val options = listOf("mais recentes", "mais lidos")
                            Row() {
                                options.forEach { item ->
                                    Row(
                                        modifier = Modifier.selectable(
                                            selected = selectedValue == item,
                                            onClick = {
                                                selectedValue = item
//                                    onSelectionChanged(item)
                                            }
                                        ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = if (options[0] == item && selectedValue.isEmpty()) true else selectedValue == item,
                                            onClick = {
                                                selectedValue = item
//                                    onSelectionChanged(item)
                                            }
                                        )
                                        Text(item)
                                    }
                                }
                            }


                        }

                        Divider(
                            thickness = 1.dp,
                            color = colorResource(id = R.color.eulirio_purple_text_color_border)
                        )

                        Column(
                            modifier = Modifier
                                .padding(top = 14.dp, bottom = 12.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "Preço",
                                    fontSize = 16.sp,
                                    fontFamily = MontSerratBold

                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                TextField(
                                    value = minValue,
                                    onValueChange = {
                                        // previnindo bug, caso o input fique vazio e, caso tenha algum valor, pegando o último valor do 'it' (último caracter digitado)
                                        val lastChar = if (it.isEmpty()) it
                                        else it.get(it.length - 1) // '.get': recupera um caracter de acordo com a posição do vetor que é passada no argumento da função
                                        Log.i("console log", lastChar.toString()) // equivalente ao 'console.log'

                                        // verifica se o último caracter é indesejado
                                        val newValue =
                                            if (lastChar == ' ' || lastChar == '-') it.dropLast(1)  // se sim, remove 1 caracter 'do final para o começo'
                                            else it // se não, o valor digitado é mantido intacto

                                        minValue = newValue
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Mínimo",
                                            fontSize = 16.sp
                                        )

                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier
                                        .width(120.dp)
                                        .padding(end = 12.dp)
                                )

                                TextField(
                                    value = maxValue,
                                    onValueChange = {
                                        // previnindo bug, caso o input fique vazio e, caso tenha algum valor, pegando o último valor do 'it' (último caracter digitado)
                                        val lastChar = if (it.isEmpty()) it
                                        else it.get(it.length - 1) // '.get': recupera um caracter de acordo com a posição do vetor que é passada no argumento da função
                                        Log.i("console log", lastChar.toString()) // equivalente ao 'console.log'

                                        // verifica se o último caracter é indesejado
                                        val newValue =
                                            if (lastChar == ' ' || lastChar == '-') it.dropLast(1)  // se sim, remove 1 caracter 'do final para o começo'
                                            else it // se não, o valor digitado é mantido intacto

                                        maxValue = newValue
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Maximo",
                                            fontSize = 16.sp
                                        )

                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                       imeAction = ImeAction.Done
                                    ),
                                    modifier = Modifier
                                        .width(120.dp),
                                    keyboardActions = KeyboardActions (
                                        onDone = {
                                            focusManager.clearFocus()
                                        }
                                    )
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(onClick = {
                            val genresChecked = Genres(genres)

                            CallSearchaAPI.filterAnnouncements(genresChecked, if (minValue.isEmpty()) "0" else minValue.replace(',', '.'), maxValue.replace(',', '.'), userID.idUser) {
                                if (it.isNullOrEmpty()) announcementIsNull.value = true
                                else {
                                    announcements.value = it
                                    announcementIsNull.value = false
                                }

                                menuState.value = !menuState.value
                            }

                            CallSearchaAPI.searchShortStoriesByGenres(genresChecked, userID.idUser) {
                                if (it.isNullOrEmpty()) shortStoryIsNull.value = true
                                else {
                                    shortStories.value = it
                                    shortStoryIsNull.value = false
                                }
                            }
                        }) {
                            Icon(
                                Icons.Rounded.Check,
                                contentDescription = "confirmar pesquisa"
                            )

                        }
                    }
                }


            }
        }
    }

    if (!fabState.value) ButtonsPost(navController, context, 12.dp, fabState) {
        fabState.value = it
    }
}

@Composable
fun TopBarSearch(
    userID: UserID,
    scaffoldState: ScaffoldState,
    state: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    tabIndex: MutableState<Int>
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
                                    .padding(0.dp),
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

                                        CallSearchaAPI.searchAnnouncementsByName(
                                            searchState.value,
                                            userID.idUser
                                        ) {
                                            if (it.isNullOrEmpty()) announcementIsNull.value = true
                                            else {
                                                announcements.value = it
                                                announcementIsNull.value = false
                                            }
                                        }

                                        CallSearchaAPI.searchShortStoriesByName(
                                            searchState.value,
                                            userID.idUser
                                        ) {
                                            if (it.isNullOrEmpty()) shortStoryIsNull.value = true
                                            else {
                                                shortStories.value = it
                                                shortStoryIsNull.value = false
                                            }
                                        }

                                        CallSearchaAPI.searchAuthorByName(
                                            searchState.value, userID.idUser
                                        ){
                                            if(it.isNullOrEmpty()) authorsIsNull.value = true
                                            else{
                                                authors.value = it
                                                authorsIsNull.value = false
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

                        if (tabIndex.value != 2) Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu burguer",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    fabVisibility.value = !fabVisibility.value
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
    topBarState: MutableState<Boolean>,
    bottomBarLength: Dp,
    navController: NavController,
    tabIndex: MutableState<Int>
) {
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val scrollStateAnn = rememberLazyListState()
    val scrollStateSS = rememberLazyListState()
    var scroll by remember { mutableStateOf(0) }

    val tabs = listOf("Livros", "Pequenas Histórias", "Autores")



    LaunchedEffect(scrollStateAnn.firstVisibleItemIndex) {
        if (scrollStateAnn.firstVisibleItemIndex > scroll) {
            topBarState.value = false
            scroll = scrollStateAnn.firstVisibleItemIndex
        } else if (scrollStateAnn.firstVisibleItemIndex < scroll) {
            topBarState.value = true
            scroll = scrollStateAnn.firstVisibleItemIndex
        }
    }

    LaunchedEffect(scrollStateSS.firstVisibleItemIndex) {
        if (scrollStateSS.firstVisibleItemIndex > scroll) {
            topBarState.value = false
            scroll = scrollStateSS.firstVisibleItemIndex
        } else if (scrollStateSS.firstVisibleItemIndex < scroll) {
            topBarState.value = true
            scroll = scrollStateSS.firstVisibleItemIndex
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
//            selectedTabIndex = tabIndex.currentPage,
            selectedTabIndex = tabIndex.value,
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
                    selected = tabIndex.value == index,
                    onClick = { tabIndex.value = index },
                )
            }
        }
        when (tabIndex.value) {
            0 -> {
                if (announcementIsNull.value) Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Não existem livros com esse nome.",
                        textAlign = TextAlign.Center,
                        fontFamily = MontSerratBold
                    )
                }

                else LazyColumn(
                    state = scrollStateAnn,
                    contentPadding = PaddingValues(bottom = bottomBarLength)
                ) {
                    items(announcements.value) {
                        AnnouncementCard(it, userID, navController, 1, true, true)
                    }
                }
            }
            1 -> {
                if (shortStoryIsNull.value) Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Não existem pequenas histórias com esse nome.",
                        textAlign = TextAlign.Center,
                        fontFamily = MontSerratBold
                    )
                }
                else LazyColumn(
                    state = scrollStateSS,
                    contentPadding = PaddingValues(bottom = bottomBarLength)
                ) {
                    items(shortStories.value) {
                        ShortStorysCard(it, navController, userID, true, true)
                    }
                }
            }
            2 -> {
                if(authorsIsNull.value) Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Não existem autores com esse nome.",
                        textAlign = TextAlign.Center,
                        fontFamily = MontSerratBold
                    )
                }
                else LazyColumn(
                    state = scrollStateSS,
                    contentPadding = PaddingValues(bottom = bottomBarLength)
                ){
                    items(authors.value){
                        GenerateAuthorCard(autor = it, navController = navController, usuarioID = userID, true)
                    }
                }


            }
        }
    }

}



