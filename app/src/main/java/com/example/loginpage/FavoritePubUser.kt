package com.example.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.loginpage.models.User
import com.example.loginpage.ui.theme.LoginPageTheme

class FavoritePubUser : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun PubFavoritas(
    navController: NavController,
    userID: Int
) {

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview4() {
//    LoginPageTheme {
//        Greeting2("Android")
//    }
//}