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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.IntroPt2
import com.example.loginpage.ui.theme.LoginPageTheme

class IntroPt3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    IntroductionPt3()
                }
            }
        }
    }
}

@Composable
fun IntroductionPt3() {
    val context = LocalContext.current

    //Content
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        //Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_eu_lirio),
                contentDescription = "",
                modifier = Modifier.size(150.dp)
            )
        }

        //IMAGE
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.intro_img_03),
                contentDescription = "",
                modifier = Modifier.size(300.dp)
            )
        }

        //MAIN
        Card(
            shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(156, 135, 193, 155))
                    .padding(start = 32.dp, end = 32.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.title3),
                    color = Color(75, 14, 101, 255),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.subtitle3),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.text3),
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(10.dp))


                //row button
                Row(modifier =
                Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                            val intent = Intent(context, IntroPt2::class.java)
                            context.startActivity(intent)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            Color(56, 24, 133, 255)
                        )
                    ) {
                        Icon(
                            Icons.Default.ChevronLeft,
                            contentDescription = "seta para a esquerda",
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 24.dp))


                    Button(
                        onClick = {
                            val intent = Intent(context, LoginPage::class.java)
                            context.startActivity(intent)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            Color(56, 24, 133, 255)
                        )
                    ) {
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "seta para a direita",
                            modifier = Modifier.size(16.dp),
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
fun DefaultPreview3() {
    LoginPageTheme {
        IntroductionPt3()
    }
}