package com.example.loginpage

import android.os.Bundle
import android.view.textclassifier.TextClassifier
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.ui.theme.LoginPageTheme

class UpdateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UpdatePage()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdatePage() {
    var nameState by remember {
        mutableStateOf("")
    }

    var userNameState by remember {
        mutableStateOf("")
    }

    var biographyState by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .height(48.dp)
                    .padding(start = 12.dp)
            )
        }

        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Row( // Row para conter a foto, o nome e o username do usuário
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier
                                .height(70.dp)
                                .width(70.dp),
                            shape = RoundedCornerShape(35.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_icone_eulirio),
                                contentDescription = ""
                            )
                        }

                        Column() {
                            OutlinedTextField(
                                value = nameState,
                                onValueChange = {
                                    nameState = it
                                },
                                modifier = Modifier
                                    .height(60.dp)
                                    .widthIn(200.dp, 250.dp)
                                    .background(colorResource(id = R.color.eulirio_light_yellow_background)),
                                label = {
                                    Text(stringResource(id = R.string.name))
                                },
                                singleLine = false,
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            OutlinedTextField(
                                value = userNameState,
                                onValueChange = {
                                    userNameState = it
                                },
                                modifier = Modifier
                                    .height(60.dp)
                                    .widthIn(200.dp, 250.dp)
                                    .background(colorResource(id = R.color.eulirio_light_yellow_background)),
                                label = {
                                    Text(stringResource(id = R.string.user_name))
                                },
                                singleLine = false
                            )
                        }
                    }
                    OutlinedTextField(
                        value = biographyState,
                        onValueChange = {
                            biographyState = it
                        },
                        modifier = Modifier
                            .heightIn(115.dp)
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.eulirio_light_yellow_background)),
                        label = {
                            Text(stringResource(id = R.string.biography))
                        },
                        singleLine = false
                    )
                }

                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, colorResource(id = R.color.eulirio_red)),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_light_yellow_background))
                ) {
                    Text(
                        text = stringResource(id = R.string.text_delete_account).uppercase(),
                        color = colorResource(id = R.color.eulirio_red)
                    )
                }
            }
        }
    }
}

fun TextField() {

}