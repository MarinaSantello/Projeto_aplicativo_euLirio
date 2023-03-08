package com.example.loginpage

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.ui.theme.LoginPageTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.log

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
                .ofPattern("yyyy MMM dd")
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
                                    Icons.Outlined.VerifiedUser,
                                    contentDescription = "Icone de confirmação",
                                    modifier = Modifier.height(24.dp),
                                    tint = colorIconUser
                                )
                            },

                            isError = userErrorRequiredInput
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        val dateDialogState = rememberMaterialDialogState()

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                color = colorResource(id = R.color.eulirio_purple_text_color),
                                text = stringResource(id = R.string.date_of_birth),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
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

                            Spacer(modifier = Modifier.height(20.dp))

                            if(pickedDate == LocalDate.now()){
                                Text(
                                    text = "Selecione sua data de nascimento",
                                    color = dateColor,
                                    fontSize = 18.sp,

                                    )
                            }
                            else{
                                Text(
                                    text = "$formattedDate",
                                    color = colorResource(id = R.color.eulirio_purple_text_color),
                                    fontSize = 18.sp,

                                    )
                            }



                            Spacer(modifier = Modifier.height(0.dp))

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

                            Spacer(modifier = Modifier.height(20.dp))

                            val context = LocalContext.current
                            val intent = Intent(context, RegisterPageThirdPart::class.java)

                            Button(
                                onClick = {
                                    if(userName.isEmpty()) {
                                        userErrorRequiredInput = true
                                        colorIconUser = Color(0xFFB00020)
                                        userFocusRequester.requestFocus()
                                    }
                                    else userErrorRequiredInput = false


                                    //Validar se o usuario escolheu uma data
                                    if(pickedDate == LocalDate.now()){
                                        dateErrorRequiredInput = true
                                        dateColor = Color(0xFFB00020)
                                    }
                                    else dateErrorRequiredInput = false

                                    if(!userErrorRequiredInput && !dateErrorRequiredInput)
                                        context.startActivity(intent)
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