package com.example.loginpage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.user.UserCall
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Genre
import com.example.loginpage.models.Tag
import com.example.loginpage.models.User
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.Montserrat
import com.example.loginpage.ui.theme.Montserrat2
//import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeBooks()
                }
            }
        }
    }
}

//@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeBooks() {

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
        bottomBar = { BottomBar(bottomBarState) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (fabState.value) {
                FloatingActionButton() {
                    fabState.value = it
                }
            }
        },
        drawerContent = {
            DrawerDesign(userID, context, scaffoldState)
        },
//
//        drawerGesturesEnabled = true,
    ) {it
        ShowBooks()
    }

    if(!fabState.value) ButtonsPost(navController, context) {
        fabState.value = it
    }
}

@Composable
fun ButtonsPost (
    navController: NavController,
    context: Context,
    onChecked: (Boolean) -> Unit ) {

    Box (
        Modifier
            .fillMaxSize()
            .background(Color(0x80000000))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 72.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier.padding(end = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color(0x80000000),
                    elevation = 0.dp
                ) {
                    Text(
                        text = "Recomendação",
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                }
                FloatingActionButton(onClick = { /* do something */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "plus", tint = Color.White)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier.padding(end = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color(0x80000000),
                    elevation = 0.dp
                ) {
                    Text(
                        text = "Pequena história",
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                }
                FloatingActionButton(onClick = { /* do something */ }, modifier = Modifier.padding(top = 8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "plus", tint = Color.White)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier.padding(end = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color(0x80000000),
                    elevation = 0.dp
                ) {
                    Text(
                        text = "E-book",
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                }
                FloatingActionButton(
                    onClick = {
//                        navController.navigate(Routes.PostEbook.name) {
//                            popUpTo(Routes.PostEbook.name) {
//                                inclusive = true
//                            }
//                        }
                        val intent = Intent(context, PostEbook::class.java)
                        context.startActivity(intent)

                        onChecked.invoke(true)
                    },
                    modifier = Modifier.padding(top = 8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "plus", tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun TopBar(
    userID: UserID,
    scaffoldState: ScaffoldState,
    state: MutableState<Boolean>
) {

    var foto by remember {
        mutableStateOf("")
    }

    CallAPI.getUser(userID){
        foto = it.foto
    }

    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = state.value,
        modifier = Modifier.background(Color.Red),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_icone_eulirio),
                        contentDescription = "logo aplicativo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 44.dp),
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

//                        Icon(
//                            Icons.Filled.Menu,
//                            contentDescription = "Localized description"
//                        )
                    }
                },
                backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
                elevation = 0.dp
            )
        }
    )
}

@Composable
fun BottomBar(state: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar{Text("Bottom Bar")}
        }
    )
}

@Composable
fun FloatingActionButton( onChecked: (Boolean) -> Unit ) {

    var showAdditionalButtons by remember { mutableStateOf(false) }

    androidx.compose.material.FloatingActionButton(
        onClick = {
            onChecked.invoke(showAdditionalButtons)

            showAdditionalButtons = !showAdditionalButtons
        }
    ) {
        Icon(Icons.Default.Add, contentDescription = "plus", tint = Color.White)
    }
}

@Composable
fun DrawerDesign(
    userID: UserID,
    context: Context,
    scaffoldState: ScaffoldState
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

    CallAPI.getUser(userID){
        foto = it.foto
        nome = it.nome
        userName = it.userName
        tags = it.tags
    }

    val auth = FirebaseAuth.getInstance()
    val clickUserPage = remember { mutableStateOf(false) }

    val intent = Intent(context, UserPage::class.java)
    val intentLogin = Intent(context, LoginPage::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.eulirio_yellow_card_background))
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
                        context.startActivity(intent)

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
            backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
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
                            context.startActivity(intent)

                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        .fillMaxWidth(),
                    backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
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
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "MEU PERFIL",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel de favoritos do usuario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
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
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "FAVORITOS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel de lidos do usuario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
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
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "LIDOS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel dos e-books do usuario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.ShoppingBag,
                            contentDescription = "Icone de sacola de compras",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(50.dp),
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "MEUS E-BOOKS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel para o usuario visualizar suas publicações
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
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
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "MINHAS OBRAS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = R.color.eulirio_black),
                            fontWeight = FontWeight.Black
                        )
                    }

                }

                //Card clicavel para o usuario adquirir a conta premium
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.WorkspacePremium,
                            contentDescription = "Icone de premium",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(50.dp),
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                        Text(
                            text = "LÍRIO PLUS",
                            fontSize = 16.sp,
                            fontFamily = Montserrat2,
                            color = colorResource(id = R.color.eulirio_black),
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

                       context.startActivity(intentLogin)
                   }
               ,
               tint = Color.Black
           )

       }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginPageTheme {
        HomeBooks()
    }
}