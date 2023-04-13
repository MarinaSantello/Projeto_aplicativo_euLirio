package com.example.loginpage

import android.os.Bundle
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
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
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

        composable(
            "${Routes.Ebook.name}/{itemId}",
            //arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) {
            val announcementId = it.arguments!!.getInt("itemId")
            EbookView(announcementId)
        }

//        composable(Routes.PostEbook.name) {
//            InputDataEbook(navController)
//        }
    }
}


