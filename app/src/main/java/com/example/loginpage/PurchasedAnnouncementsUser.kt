package com.example.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.buy.CallBuyAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.MontSerratBold

class PurchasedAnnouncementsUser : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PurchasedAnnouncementsPage(rememberNavController())
                }
            }
        }
    }
}

@Composable
fun PurchasedAnnouncementsPage(navController: NavController) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }

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
        ShowBooks(users[0].idUser, it.calculateBottomPadding(), 7, topBarState, bottomBarState, navController, null)
    }
}

@Composable
fun PurchasedAnnouncementsView(
    userID: Int,
    bottomBarLength: Dp,
    navController: NavController
) {

    var announcements by remember {
        mutableStateOf(listOf<AnnouncementGet>())
    }
    var announcementIsNull by remember {
        mutableStateOf(false)
    }

    CallBuyAPI.getPurchasedAnnouncement(userID) {
        if (it.isNullOrEmpty()) announcementIsNull = true
        else announcements = it
    }

    if (announcementIsNull) Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Você não comprou nenhum livro ainda.",
            textAlign = TextAlign.Center,
            fontFamily = MontSerratBold
        )
    }

    else LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
        items(announcements) {
            AnnouncementCard(it, userID, navController, 1, true, true)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview5() {
//    LoginPageTheme {
//        Greeting2("Android")
//    }
//}