package com.example.loginpage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.API.follow.CallFollowAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.Genero
import com.example.loginpage.models.UserFollow
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.GenerateAuthorCard
import com.example.loginpage.ui.theme.LoginPageTheme

//class FollowPage : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    ViewFollowPage(rememberNavController(), 0)
//                }
//            }
//        }
//    }
//}

@Composable
fun ViewFollowPage(
    navController: NavController,
    visitedUser: Int,
    type: Int
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBarShop(navController) }
    ) {it
        ShowFollow(visitedUser, users[0].idUser, navController, type)
    }
}

@Composable
fun ShowFollow (
    userID: Int,
    currentUser: Int,
    navController: NavController,
    type: Int
){
    var followsIsNull by remember {
        mutableStateOf(false)
    }
    var follows by remember {
        mutableStateOf(listOf<UserFollow>())
    }

    var nullText by remember {
        mutableStateOf("")
    }
    Log.i("tipo", type.toString())

    when (type){
        0 -> CallFollowAPI.getFollowers(userID, currentUser) {
            if (it.isNullOrEmpty()) {
                followsIsNull = true

                nullText = "Este usuário não possui seguidores."
            }

            else follows = it
        }

        1 -> CallFollowAPI.getFollowing(userID, currentUser) {
            if (it.isNullOrEmpty()) {
                followsIsNull = true

                nullText = "Este usuário não está seguindo nenhum perfil."
            }

            else follows = it
        }
    }

    if (followsIsNull) Text(text = nullText)

    else LazyColumn() {
        items(follows) {
            GenerateAuthorCard(it, navController, currentUser, false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    LoginPageTheme {
        ViewFollowPage(rememberNavController(), 0, 0)
    }
}