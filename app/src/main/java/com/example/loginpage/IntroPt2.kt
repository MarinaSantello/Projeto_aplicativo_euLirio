package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.IntroPt3
import com.example.loginpage.ui.theme.LoginPageTheme

class IntroPt2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    IntroductionPt2()
                }
            }
        }
    }
}

@Composable
fun IntroductionPt2() {
    val context = LocalContext.current

    //Content
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_eu_lirio),
                contentDescription = "Logo euLirio",
                modifier = Modifier.size(150.dp)
            )
        }

        //IMAGE
        Column(
            modifier = Modifier.fillMaxHeight(.45f)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.intro_img_02),
                contentDescription = "",
                modifier = Modifier.size(300.dp),
                alignment = Alignment.Center
            )
        }

        //MAIN
        Card(
            shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(255, 231, 176, 230))
                    .padding(start = 32.dp, end = 32.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.title2),
                    modifier = Modifier.padding(top = 20.dp, start = 24.dp, end = 24.dp),
                    color = Color(128, 97, 15, 255),
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = stringResource(id = R.string.subtitle2),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.text2),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.h3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.free),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.h3
                )

                Spacer(modifier = Modifier.height(10.dp))

                //row button
                Row(modifier =
                Modifier.fillMaxSize().padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                            val intent = Intent(context, IntroPt1::class.java)
                            context.startActivity(intent)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            Color(122, 89, 0, 255)
                        )
                    ) {
                        Icon(
                            Icons.Default.ChevronLeft,
                            contentDescription = "seta para a esquerda",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 24.dp))


                    Button(
                        onClick = {
                            val intent = Intent(context, IntroPt3::class.java)
                            context.startActivity(intent)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            Color(122, 89, 0, 255)
                        )
                    ) {
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "icon",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    LoginPageTheme {
        IntroductionPt2()
    }
}