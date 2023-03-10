package com.example.loginpage

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.ui.components.GenreCard
import com.example.loginpage.ui.theme.LoginPageTheme

class RegisterPageThirdPart : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    registerPageThirdPart(getClickState())
                }
            }
        }
    }
}

@Composable
fun getClickState(): () -> MutableState<Boolean> {
    var checkState = rememberSaveable {
        mutableStateOf(false)
    }

    return { checkState }
}

@Composable
fun registerPageThirdPart(getCLickState: () -> MutableState<Boolean>) {

    val context = LocalContext.current

    val intentThidPart = (context as RegisterPageThirdPart).intent //Intent(context, RegisterPageSecondPart::class.java)

    val nome = intentThidPart.getStringExtra("nome")
    val dataNascimento = intentThidPart.getStringExtra("data_nascimento")
    val tags = intentThidPart.getIntegerArrayListExtra("tags").toString()

    Log.i("user tags", dataNascimento.toString())

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_eu_lirio),
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .width(178.dp)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Card(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                        .height(440.dp),
                    shape = RoundedCornerShape(50.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White
                ) {
                    Box(){
                        Column(
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = "olá, [nome]".uppercase(),
                                    modifier = Modifier
                                        .padding(top = 32.dp)
                                        .fillMaxWidth(),
                                    color = colorResource(id = R.color.eulirio_purple_text_color),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h2
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                val descriptionP1 = stringResource(id = R.string.description_third_page_pt1)
                                val descriptionP2 = stringResource(id = R.string.description_third_page_pt2)
                                val descriptionP3 = stringResource(id = R.string.description_third_page_pt3)
                                val descriptionP4 = stringResource(id = R.string.description_third_page_pt4)
                                val descriptionP5 = stringResource(id = R.string.description_third_page_pt5)

                                Text(
                                    buildAnnotatedString {
                                        append("$descriptionP1 ")

                                        withStyle(style = SpanStyle(
                                            color = colorResource(id = R.color.eulirio_purple_text_color),
                                            fontWeight = FontWeight.W900
                                        )
                                        ) {
                                            append("$descriptionP2 ")
                                        }

                                        append("$descriptionP3 ")

                                        withStyle(style = SpanStyle(
                                            color = colorResource(id = R.color.eulirio_purple_text_color),
                                            fontWeight = FontWeight.W900
                                        )
                                        ) {
                                            append("$descriptionP4 ")
                                        }

                                        append(descriptionP5)
                                    },

                                    modifier = Modifier.padding(start = 34.dp, end = 34.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body2,
                                )

                            }

                            val id = listOf(1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 21, 32, 54, 77, 23, 25)
                            val idKey = listOf(1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 21, 32, 54, 77, 23, 25)

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),

                                // content padding
                                contentPadding = PaddingValues(
                                    start = 24.dp,
                                    end = 24.dp,
                                    top = 4.dp,
                                    bottom = 70.dp
                                )
                            ) {
                                items(
                                    items = id
                                ) {
                                    GenreCard(it, getCLickState)
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(Color(0x80FFFFFF))
                                .fillMaxWidth()
                                .height(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                var teste = getCLickState()
                                Log.i("teste-state", teste.toString())

                            },
                                modifier = Modifier
                                    .width(200.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_purple))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.finish),
                                    color = colorResource(
                                        id = R.color.white
                                    ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun registerPageThirdPartPreview() {
    registerPageThirdPartPreview()
}