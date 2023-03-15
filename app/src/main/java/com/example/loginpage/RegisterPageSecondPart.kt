package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    val pickedTime by remember {
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
                        .padding(start = 24.dp, end = 24.dp)
                        .height(440.dp),
                    shape = RoundedCornerShape(50.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 40.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.your_data),
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .fillMaxWidth(),
                            color = colorResource(id = R.color.eulirio_purple_text_color),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

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
                                    Text(
                                        text = stringResource(id = R.string.name),
                                        fontSize = 16.sp
                                    )
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
                                    //CheckBox da Tag1
                                    Checkbox(
                                        checked = checkStateTag1,
                                        onCheckedChange = {
                                            checkStateTag1 = !checkStateTag1
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = colorResource(id = R.color.eulirio_purple),
                                            uncheckedColor = Color.Gray
                                        )
                                    )

                                    Text(
                                        color = colorResource(id = R.color.eulirio_purple_text_color),
                                        text = stringResource(id = R.string.tag_1_name),
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp,
                                        modifier = Modifier.padding(end = 7.dp)


                                    )
                                    //CheckBox da tag2
                                    Checkbox(
                                        checked = checkStateTag2,
                                        onCheckedChange = {
                                            checkStateTag2 = !checkStateTag2

                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = colorResource(id = R.color.eulirio_purple),
                                            uncheckedColor = Color.Gray
                                        ),
                                        modifier = Modifier.padding(start = 7.dp)
                                    )

                                    Text(
                                        color = colorResource(id = R.color.eulirio_purple_text_color),
                                        text = stringResource(id = R.string.tag_2_name),
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp,
                                    )


                                }

                                if (tagsErrorRequired) Text(
                                    text = "Esta informação é obrigatória",
                                    color = Color(0xFFB00020),
                                    fontSize = 15.sp,
                                )

                                val context = LocalContext.current
                                val intent = Intent(context, RegisterPageThirdPart::class.java)

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

                                            val intentFirstPart =
                                                (context as RegisterPageSecondPart).intent //Intent(context, RegisterPageSecondPart::class.java)

                                            val userNameIntent =
                                                intentFirstPart.getStringExtra("user1")
                                            val email = intentFirstPart.getStringExtra("email1")
                                            val senha = intentFirstPart.getStringExtra("senha1")

                                            intent.putExtra("nome", userName)
                                            intent.putExtra("user", userNameIntent)
                                            intent.putExtra("email", email)
                                            intent.putExtra("senha", senha)
                                            intent.putExtra(
                                                "data_nascimento",
                                                pickedDate.toString()
                                            )

                                            intent.putIntegerArrayListExtra("tags", tags)

                                            context.startActivity(intent)
                                        }
                                    },modifier = Modifier
                                        .width(200.dp)
                                        .padding(top = 24.dp),
                                    shape = RoundedCornerShape(30.dp),
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_purple))


                                ) {
                                    Text(
                                        text = stringResource(id = R.string.advance),
                                        color = colorResource(
                                            id = R.color.white
                                        ),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
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
}

@Preview(showBackground = true)
@Composable
fun registerPageSecondPartPreview() {
    registerPageSecondPartPreview()
}