package com.example.loginpage

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.loginpage.constants.Routes
import com.example.loginpage.ui.theme.LoginPageTheme
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavController,
           userState: Boolean
) = Box(modifier = Modifier
    .fillMaxSize()
    .background(
        Color(242, 242, 242, 255)
    ))
{

    val scale = remember {
        Animatable(0.0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(1000)
        if (userState) navController.navigate(Routes.Home.name) {
            popUpTo(Routes.Home.name) {
                inclusive = true
            }
        }

//        else if (usersLogin) navController.navigate(Routes.Login.name) {
//            popUpTo(Routes.Login.name) {
//                inclusive = true
//            }
//        }

        else navController.navigate(Routes.Introduction.name) {
            popUpTo(Routes.Introduction.name) {
                inclusive = true
            }
        }
    }

    Icon(Icons.Default.Circle, contentDescription = "",
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxSize(.5f),
        tint = colorResource(id = R.color.eulirio_purple_text_color))
    Image(
        painter = painterResource(id = R.drawable.logo_icone_eulirio),
        contentDescription = "Logo do app",
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxSize(.35f)
    )

    Column(modifier = Modifier.align(Alignment.BottomCenter)) {
        Text(
            text = "FROM",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1)
        Image(
            painter = painterResource(id = R.drawable.logo_icone_iara),
            contentDescription = "Logo da empresa",
            modifier = Modifier
                .fillMaxSize(.1f)
        )
    }
}