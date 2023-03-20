package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.ui.theme.LoginPageTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class RegisterPageSecondPart : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    registerPageSecondPart()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun registerPageSecondPart() {


    var userName by rememberSaveable {
        mutableStateOf("")
    }

    val dateDialogState = rememberMaterialDialogState()

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }


    var checkStateTag1 by remember {
        mutableStateOf(false)
    }

    var checkStateTag2 by remember {
        mutableStateOf(false)
    }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("yyyy-MMM-dd")
                .format(pickedDate)
        }

    }

    var colorIconUser = colorResource(id = R.color.eulirio_purple_text_color_border)
    var dateColor = colorResource(id = R.color.eulirio_purple_text_color_border)

    var dateErrorRequiredInput by remember {
        mutableStateOf(false)
    }

    var userErrorRequiredInput by remember {
        mutableStateOf(false)
    }

    var tagsErrorRequired by remember {
        mutableStateOf(false)
    }

    val userFocusRequester = remember {
        FocusRequester()
    }

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
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
                        .height(445.dp)
                        .width(325.dp),
                    shape = RoundedCornerShape(50.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 40.dp, bottom = 45.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            color = colorResource(id = R.color.eulirio_purple_text_color),
                            text = stringResource(id = R.string.your_data),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                        OutlinedTextField(
                            value = userName, onValueChange = {
                                userName = it;
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp)
                                .focusRequester(userFocusRequester),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            label = {
                                Text(text = stringResource(id = R.string.name), fontSize = 16.sp)
                            },


                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = "Icone de confirmação",
                                    modifier = Modifier.height(24.dp),
                                    tint = colorIconUser
                                )
                            },

                            isError = userErrorRequiredInput,

                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                color = colorResource(id = R.color.eulirio_purple_text_color),
                                text = stringResource(id = R.string.date_of_birth).uppercase(),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    dateDialogState.show()
                                },
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(200.dp)
                                    .border(
                                        2.dp,
                                        colorResource(id = R.color.eulirio_purple),
                                        RoundedCornerShape(30.dp)
                                    ),
                                shape = RoundedCornerShape(30.dp),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),


                                ) {

                                Text(
                                    text = "Escolha uma data",
                                    color = colorResource(id = R.color.eulirio_purple_text_color)
                                )

                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (pickedDate == LocalDate.now()) {
                                Text(
                                    text = "Selecione sua data de nascimento",
                                    color = dateColor,
                                    fontSize = 15.sp,
                                )
                            } else {
                                Text(
                                    text = "$formattedDate",
                                    color = colorResource(id = R.color.eulirio_purple_text_color),
                                    fontSize = 15.sp,

                                    )
                            }



                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                //Card de escritor
                                Card(
                                    modifier = Modifier
                                        .height(28.dp)
                                        .width(101.dp)
                                        .padding(end = 5.dp)
                                        .clickable { checkStateTag1 = !checkStateTag1},
                                    shape = RoundedCornerShape(100.dp),
                                    elevation = 0.dp,
                                    border = BorderStroke(
                                        1.dp,
                                        colorResource(id = R.color.eulirio_purple_text_color_border)
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Escritor",
                                            color = colorResource(id = R.color.eulirio_purple_text_color_border),
                                            fontWeight = FontWeight.SemiBold

                                        )
                                    }

                                    if (checkStateTag1) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentAlignment = Alignment.CenterEnd


                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .height(28.dp)
                                                    .width(28.dp),
                                                backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                                            ) {
                                                Icon(
                                                    Icons.Outlined.Done,
                                                    contentDescription = "verificação",
                                                    tint = Color.White
                                                )

                                            }

                                        }

                                    }


                                }

                                //Card para leitor
                                Card(
                                    modifier = Modifier
                                        .height(28.dp)
                                        .width(101.dp)
                                        .padding(start = 5.dp)
                                        .clickable { checkStateTag2 = !checkStateTag2},
                                    shape = RoundedCornerShape(100.dp),
                                    elevation = 0.dp,
                                    border = BorderStroke(
                                        1.dp,
                                        colorResource(id = R.color.eulirio_purple_text_color_border)
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Leitor",
                                            color = colorResource(id = R.color.eulirio_purple_text_color_border),
                                            fontWeight = FontWeight.SemiBold

                                        )
                                    }

                                    if (checkStateTag2) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentAlignment = Alignment.CenterEnd


                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .height(28.dp)
                                                    .width(28.dp),
                                                backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                                            ) {
                                                Icon(
                                                    Icons.Outlined.Done,
                                                    contentDescription = "verificação",
                                                    tint = Color.White
                                                )

                                            }

                                        }

                                    }


                                }


                            }

                            if (tagsErrorRequired) Text(
                                text = "Esta informação é obrigatória",
                                color = Color(0xFFB00020),
                                fontSize = 15.sp,
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            val context = LocalContext.current
                            val intent = Intent(context, RegisterPageThirdPart::class.java)

                            val email = intent.getStringExtra("email1")
                            val senha = intent.getStringExtra("senha1")
                            val user = intent.getStringExtra("user1")

                            Button(
                                onClick = {
                                    if (userName.isEmpty()) {
                                        userErrorRequiredInput = true
                                        colorIconUser = Color(0xFFB00020)
                                        userFocusRequester.requestFocus()
                                    } else userErrorRequiredInput = false


                                    //Validar se o usuario escolheu uma data
                                    if (pickedDate == LocalDate.now()) {
                                        dateErrorRequiredInput = true
                                        dateColor = Color(0xFFB00020)
                                    } else dateErrorRequiredInput = false

                                    val tags = ArrayList<Int>()

                                    if (checkStateTag1)
                                        tags.add(1)

                                    if (checkStateTag2)
                                        tags.add(2)

                                    tagsErrorRequired = tags.size <= 0

                                    if (!userErrorRequiredInput && !dateErrorRequiredInput && !tagsErrorRequired) {

                                        intent.putExtra("nome", userName)
                                        intent.putExtra("data_nascimento", pickedDate.toString())
                                        intent.putExtra("email", email)
                                        intent.putExtra("senha", senha)
                                        intent.putExtra("user", user)

                                        intent.putIntegerArrayListExtra("tags", tags)

                                        context.startActivity(intent)
                                    }
                                },
                                modifier = Modifier
                                    .width(160.dp)
                                    .height(50.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_purple))


                            ) {
                                Text(
                                    text = stringResource(id = R.string.advance),
                                    color = colorResource(
                                        id = R.color.white
                                    ),
                                    fontSize = 18.sp
                                )
                            }


                        }



                        MaterialDialog(
                            dialogState = dateDialogState,
                            buttons = {
                                positiveButton(text = "Ok")
                                negativeButton(text = "Cancel")
                            }
                        ) {
                            datepicker(
                                initialDate = LocalDate.now(),
                                title = "Pick a Date",
                                allowedDateValidator = {
                                    var validate = 0
                                    val currentYear = LocalDate.now().year.toInt()

                                    validate = currentYear - it.year.toInt()

                                    validate >= 18


                                }
                            ) {
                                pickedDate = it
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
fun registerPageSecondPartPreview() {
    registerPageSecondPartPreview()
}