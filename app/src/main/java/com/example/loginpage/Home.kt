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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.resources.TopBar
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
                    val navController = rememberNavController()

                    HomeBooks(navController)
                }
            }
        }
    }
}

//@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeBooks(navController: NavController) {

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
            DrawerDesign(userID, context, scaffoldState, navController)
        },
//
//        drawerGesturesEnabled = true,
    ) {
        ShowBooks(users[0].idUser, it.calculateBottomPadding(), 1, navController)
    }

    if(!fabState.value) ButtonsPost(navController, context) {
        fabState.value = it
    }
}

@Composable
fun ButtonsPost (
    navController: NavController,
    context: Context,
    onChecked: (Boolean) -> Unit
) {

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
                // Creating an annonated string
                val mAnnotatedLinkString = buildAnnotatedString {

                    // creating a string to display in the Text
                    val mStr = stringResource(id = R.string.texto_publicacao_curta)

                    // word and span to be hyperlinked
                    val mStartIndex = mStr.indexOf("plataforma web")
                    val mEndIndex = mStartIndex + 14

                    append(mStr)
                    addStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.eulirio_yellow_card_background),
                            textDecoration = TextDecoration.Underline
                        ), start = mStartIndex, end = mEndIndex
                    )

                    // attach a string annotation that
                    // stores a URL to the text "link"
                    addStringAnnotation(
                        tag = "URL",
                        annotation = "https://github.com/rafaeloliveira3/eu-lirio-project-front",
                        start = mStartIndex,
                        end = mEndIndex
                    )

                }

                // UriHandler parse and opens URI inside
                // AnnotatedString Item in Browse
                val mUriHandler = LocalUriHandler.current

                Card(
                    modifier = Modifier
                        .fillMaxWidth(.6f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color(0x80000000),
                    elevation = 0.dp
                ) {
                    ClickableText(
                        text = mAnnotatedLinkString,
                        onClick = {
                            mAnnotatedLinkString
                                .getStringAnnotations("URL", it, it)
                                .firstOrNull()?.let { stringAnnotation ->
                                    mUriHandler.openUri(stringAnnotation.item)
                                }
                        },
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(Color.White),
                )
//                    Text(
//                        text = stringResource(id = R.string.texto_publicacao_curta),
//                        modifier = Modifier.padding(8.dp),
//                        color = Color.White
//                    )
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