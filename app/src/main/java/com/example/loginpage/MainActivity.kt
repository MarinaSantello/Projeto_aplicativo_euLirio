package com.example.loginpage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.constants.Routes
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.ui.components.AnnouncementCard
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}


@Composable
fun Greeting() {
    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser // retorna a conta que está autenticada no dispositivo (se não tiver usuario, ele é nulo)
    val userAuth = currentUser != null


    val userID = UserIDrepository(context).getAll()[0].idUser
    // registrando o id do usuário no sqlLite
//    val userIDRepository = UserIDrepository(context)
//    val users = userIDRepository.getAll()
//    val usersLogin = users[0].id < 0

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SplashScreen.name) {
        composable(Routes.SplashScreen.name) {
            Splash(navController = navController, userAuth)
        }

        composable(Routes.Introduction.name) {
            IntroductionPt1()
        }

        composable(Routes.Login.name) {
            loginPage()
        }

        composable(Routes.Home.name) {
            HomeBooks(navController)
        }

        composable(Routes.UserStories.name) {
            ShowUserStories(navController)
        }

        composable(Routes.PostEbook.name) {
            PostDataEbook(navController)
        }

        composable(
            "${Routes.Ebook.name}/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) {
            val announcementId = it.arguments!!.getInt("itemId")

            EbookView(announcementId, navController)
        }

        composable(
            "${Routes.EditEbook.name}/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) {
            val announcementId = it.arguments!!.getInt("itemId")

            addDataAnnouncement(userID, announcementId)
            EditDataEbook(announcementId, navController)
        }

        composable(
            "${Routes.ShortStory.name}/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) {
            val shortStoryId = it.arguments!!.getInt("itemId")

            ShortStory(shortStoryId, navController)
        }

        composable(
            "${Routes.ShortStoryRender.name}/{itemId}/{userId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType }, navArgument("userId") { type = NavType.IntType })
        ) {
            val shortStoryId = it.arguments!!.getInt("itemId")
            val userId = it.arguments!!.getInt("userId")

            var shortStory by remember {
                mutableStateOf<ShortStoryGet?>(null)
            }

            CallShortStoryAPI.getShortStoryByID(shortStoryId, userId) { shortStoryData ->
                shortStory = shortStoryData
            }

            if (shortStory != null) ScreenBuilder(shortStory!!, navController)
        }

//        composable(Routes.PostEbook.name) {
//            InputDataEbook(navController)
//        }
    }
}