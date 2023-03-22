package com.example.loginpage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.ui.theme.LoginPageTheme
import kotlinx.coroutines.launch

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
                    homeBooks()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun homeBooks() {

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

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
            ShowDesign()
        },
        drawerGesturesEnabled = true,
    ) {
        Text(text = "teste $it")
    }

    if(!fabState.value) Box (
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
fun ShowDesign(){

    
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
                    painter = painterResource(id = R.drawable.download),
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
                        text = "Name",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "@name",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                }


            }

            Spacer(modifier = Modifier.height(10.dp))

            val tags = listOf<String>("TAG-1", "TAG-2")

            LazyRow() {
                items(tags) {
                    Card(
                        modifier = Modifier
                            .height(18.dp)
                            .padding(end = 5.dp)
                            .width(60.dp),
                        backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(
                            1.dp / 2,
                            Color.White
                        )
                    ) {
                        Text(
                            text = it,
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
                .background(colorResource(id = R.color.eulirio_light_yellow_background))
        ) {

            //Card clicavel do perfil do usuario
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 50.dp)
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
                    .padding(start = 50.dp)
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


        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginPageTheme {
        homeBooks()
    }
}