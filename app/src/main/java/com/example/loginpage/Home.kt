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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.user.UserCall
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Genre
import com.example.loginpage.models.Tag
import com.example.loginpage.models.User
import com.example.loginpage.ui.theme.LoginPageTheme
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeBooks() {

    val tag = listOf<Tag>(Tag(0, ""))
    val genero = listOf<Genero>(Genero(0, ""))

    var user = User(tags = tag, generos = genero)

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)


    val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
    val userCall = retrofit.create(UserCall::class.java) // instância do objeto contact
    val callCurrentUser = userCall.getByID(userID.id)

    callCurrentUser.enqueue(object :
        Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            user = response.body()!! ?: User(tags = tag, generos = genero)
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
        }
    })

//    Log.i("id usuario2", users.size.toString())

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
        topBar = { TopBar(scaffoldState, topBarState) },
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
            DrawerDesign(userID, user, context)
        },

        drawerGesturesEnabled = true,
    ) {
        Text(text = "teste $it")
    }

    if(!fabState.value) ButtonsPost()
}

@Composable
fun ButtonsPost () {
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
                    Icon(Icons.Default.Edit, contentDescription = "plus")
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
                    Icon(Icons.Default.Add, contentDescription = "plus")
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
                FloatingActionButton(onClick = { /* do something */ }, modifier = Modifier.padding(top = 8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "plus")
                }
            }
        }
    }
}

@Composable
fun TopBar(
    scaffoldState: ScaffoldState,
    state: MutableState<Boolean>
) {
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = state.value,
        modifier = Modifier.background(Color.Red),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = { Text("Top Bar") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
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
        Icon(Icons.Default.Add, contentDescription = "plus")
    }
}

@Composable
fun DrawerDesign(
    userID: UserID,
    user: User,
    context: Context
){

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
                .height(90.dp)
                .padding(start = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top

        ) {
            Row(
                modifier = Modifier
                    .height(60.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.foto ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                    contentDescription = "",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(100.dp))
                )

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = user.nome.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "@${user.userName}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                }


            }

            Spacer(modifier = Modifier.height(10.dp))

            val tags = user.tags

            LazyRow() {
                items(tags) {
                    Card(
                        modifier = Modifier
                            .height(18.dp)
                            .padding(end = 5.dp)
                            .width(60.dp),
                        backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                        shape = RoundedCornerShape(100.dp),
                        elevation = 0.dp,
                        border = BorderStroke(
                            1.dp / 2,
                            Color.White
                        )
                    ) {
                        Text(
                            text = it.nomeTag.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(42.dp))

        Column(
            modifier = Modifier
                .height(450.dp)
                .width(300.dp)
                .background(colorResource(id = R.color.eulirio_light_yellow_background)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Card clicavel do perfil do usuario
            Card(
                modifier = Modifier
                    .height(80.dp)
                    .padding(top = 40.dp, start = 40.dp)
                    .clickable { context.startActivity(intent) }
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
                        tint = Color.Black
                    )
                    Text(
                        text = "MEU PERFIL",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                }

            }

            //Card clicavel de favoritos do usuario
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 40.dp)
                    .fillMaxWidth(),
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
                        tint = Color.Black
                    )
                    Text(
                        text = "FAVORITOS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                }

            }

            //Card clicavel de lidos do usuario
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 40.dp)
                    .fillMaxWidth(),
                backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
                elevation = 0.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.PriorityHigh,
                        contentDescription = "ICONE DE LIDOS",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .height(50.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "LIDOS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                }

            }

            //Card clicavel dos e-books do usuario
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 40.dp)
                    .fillMaxWidth(),
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
                        tint = Color.Black
                    )
                    Text(
                        text = "MEUS E-BOOKS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                }

            }

            //Card clicavel para o usuario adquirir a conta premium
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 40.dp)
                    .fillMaxWidth(),
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
                            .height(40.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "LÍRIO PLUS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                }

            }

            //Card clicavel para o usuario editar o seu usuario
            Card(
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 40.dp, start = 40.dp)
                    .fillMaxWidth(),
                backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
                elevation = 0.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.EditNote,
                        contentDescription = "Icone de edição",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .height(50.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "EDITAR PERFIL",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
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
                       userIDRepository.delete(userID)

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