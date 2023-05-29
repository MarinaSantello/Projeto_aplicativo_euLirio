package com.example.loginpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.Charts.CreateLineChart
import com.example.loginpage.ui.components.Charts.PieChart
import com.example.loginpage.ui.components.DashCommentCard
import com.example.loginpage.ui.theme.LoginPageTheme
import kotlin.math.ceil

class DashboardPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowDashboard(navController = rememberNavController(), idAnnouncement = 1)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShowDashboard (
    navController: NavController,
    idAnnouncement: Int
) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }

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
        DashboardView(idAnnouncement)
    }
}

@Composable
fun DashboardView(
    idAnnouncement: Int
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "titulo do livro")

        CreateLineChart(43, listOf("dom", "seg", "ter", "quar", "quin", "sex", "sab"), listOf(55.0, 2.0, 18.2, 41.2876, 69.0, 23.75, 100.0))

        DashCommentCard()

        PieChart(
            data = mapOf(
                Pair("TESTE-1", 200),
                Pair("TESTE-2", 130),
                Pair("TESTE-3", 300)
            ) )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview5() {
//    LoginPageTheme {
//        Greeting2("Android")
//    }
//}