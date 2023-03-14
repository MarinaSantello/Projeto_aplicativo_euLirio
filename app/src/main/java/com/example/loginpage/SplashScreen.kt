package com.example.loginpage

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.loginpage.constants.Routes
import com.example.loginpage.ui.theme.LoginPageTheme
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavController, i: Int) = Box(modifier = Modifier.fillMaxSize())
{
    Text(text = "Teste")

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
        if (i == 0) navController.navigate(Routes.Login.name) {
            popUpTo(Routes.Login.name) {
                inclusive = true
            }
        }

        else navController.navigate(Routes.Introduction.name) {
            popUpTo(Routes.Introduction.name) {
                inclusive = true
            }
        }
    }
    Text(text = "Teste")
}