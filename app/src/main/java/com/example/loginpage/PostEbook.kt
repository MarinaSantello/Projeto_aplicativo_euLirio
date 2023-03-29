package com.example.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.Spartan

class PostEbook : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    InputDataEbook()
                }
            }
        }
    }
}

@Composable
fun InputDataEbook() {
    Column() {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.065f),
            shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier.padding(start = 24.dp, top = 16.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "icone para fechar",
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.publicar),
                    Modifier
                        .fillMaxWidth(),
                    fontFamily = Spartan,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
        }


        Row(
            Modifier.padding(20.dp, 12.dp)
        ) {

            Card(
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 8.dp, top = 8.dp),
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomEnd = 8.dp,
                    bottomStart = 8.dp
                ),
                backgroundColor = Color.LightGray
            ) {

                Text(
                    text = stringResource(id = R.string.adicionarimagem),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 26.dp),
                    fontSize = 16.sp
                )
            }


            //column titulo
            Column() {

                Text(text = stringResource(id = R.string.publicartitulo),
                    modifier = Modifier.padding(4.dp))
                Text(
                    text = stringResource(id = R.string.publicarname),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )

                Text(text = stringResource(id = R.string.precopub),
                    modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = stringResource(id = R.string.precificacaopub),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 4.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))




                Column() {

                    Text(
                        text = stringResource(id = R.string.sinopsepub),
                        modifier = Modifier.padding(start = 2.dp),
                        textAlign = TextAlign.Start
                    )

                    Text(text = stringResource(id = R.string.textsinopse),
                        fontSize = 10.sp)

                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))

                }
            }

        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview5() {
    LoginPageTheme {
        InputDataEbook()
    }
}