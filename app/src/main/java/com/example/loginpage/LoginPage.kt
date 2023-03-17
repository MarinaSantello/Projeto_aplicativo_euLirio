package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.user.UserCall
import com.example.loginpage.API.userLogin.UserLoginCall
import com.example.loginpage.models.RetornoApi
import com.example.loginpage.models.UserLogin
import com.example.loginpage.ui.theme.LoginPageTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    loginPage()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun loginPage() {

    //passar o contexto
    val context = LocalContext.current

    var emailValue by rememberSaveable {
        mutableStateOf("")
    }

    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }

    var emailErrorRequiredInput by remember {
        mutableStateOf(false)
    }

    var passwordErrorRequiredInput by remember {
        mutableStateOf(false)
    }

    var colorIconEmail = colorResource(id = R.color.eulirio_purple_text_color_border)

    var colorIconPassword = colorResource(id = R.color.eulirio_purple_text_color_border)

    val emailFocusRequester = remember {
        FocusRequester()
    }

    val passwordFocusRequester = remember {
        FocusRequester()
    }

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
                        .height(400.dp)
                        .background(Color(0x4DFFFCEA)),
                    shape = RoundedCornerShape(50.dp),
                    elevation = 2.dp,
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 36.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            color = colorResource(id = R.color.eulirio_purple_text_color),
                            text = stringResource(id = R.string.login_name),
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Black,
                            style = MaterialTheme.typography.h1
                        )

                        OutlinedTextField(
                            value = emailValue,
                            onValueChange = {
                                emailValue = it;
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, top = 12.dp, end = 24.dp)
                                .focusRequester(emailFocusRequester),
                            textStyle = TextStyle(fontSize = 16.sp),

                            shape = RoundedCornerShape(12.dp),

                            label = {
                                Text(
                                    text = stringResource(id = R.string.login_user_name),
                                    style = TextStyle (fontWeight = FontWeight.Light)
                                )
                            },

                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Icone de e-mail",
                                    modifier = Modifier.height(24.dp),
                                    tint = colorIconEmail
                                )
                            },

                            isError = emailErrorRequiredInput,

                            keyboardOptions =  KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),

                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = passwordValue,
                            onValueChange = {
                                passwordValue = it;
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp)
                                .focusRequester(passwordFocusRequester),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 16.sp),

                            label = {
                                Text(
                                    text = stringResource(id = R.string.password_name),
                                    style = TextStyle (fontWeight = FontWeight.Light)
                                )
                            },

                            leadingIcon = {
                                Icon(
                                    Icons.Default.Key,
                                    contentDescription = "Icone de cadeado",
                                    modifier = Modifier.height(24.dp),
                                    tint = colorIconPassword
                                )
                            },

                            trailingIcon = {
                                IconButton(onClick = { showPassword = !showPassword }) {
                                    Icon(
                                        modifier = Modifier.height(16.dp),
                                        contentDescription = if (showPassword) "Show Password" else "Hide Password",
                                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        tint = Color(0xFF010101)
                                    )
                                }
                            },

                            isError = passwordErrorRequiredInput,

                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),

                            keyboardOptions =  KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions (
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),

                            singleLine = true
                        )

                        if (emailErrorRequiredInput || passwordErrorRequiredInput) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 2.dp),
                                text = stringResource(id = R.string.erro_message_input_required),
                                color = Color(0xFFB00020),
                                fontSize = 12.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 30.dp)
                            ,
                            horizontalAlignment = Alignment.End,


                            ) {

                            Button(
                                onClick = {
                                    if(emailValue.isEmpty()) {
                                        emailErrorRequiredInput = true
                                        colorIconEmail = Color(0xFFB00020)
                                        emailFocusRequester.requestFocus()
                                    }
                                    else emailErrorRequiredInput = false

                                    if(passwordValue.isEmpty()) {
                                        passwordErrorRequiredInput = true
                                        colorIconPassword = Color(0xFFB00020)
                                        passwordFocusRequester.requestFocus()
                                    }
                                    else passwordErrorRequiredInput = false

                                    val userLogin = UserLogin(
                                        login = emailValue,
                                        senha = passwordValue
                                    )

                                    val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
                                    val userLoginCall = retrofit.create(UserLoginCall::class.java) // instância do objeto contact
                                    val callValidateUser = userLoginCall.validate(userLogin)

                                    var responseValidate = 0
                                    // Excutar a chamada para o End-point
                                    callValidateUser.enqueue(object :
                                        Callback<RetornoApi> { // enqueue: usado somente quando o objeto retorna um valor
                                        override fun onResponse(
                                            call: Call<RetornoApi>,
                                            response: Response<RetornoApi>
                                        ) {
                                            responseValidate = response.code()

                                            if (responseValidate == 200){
                                                val intent = Intent(context, Home::class.java)
                                                context.startActivity(intent)
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<RetornoApi>,
                                            t: Throwable
                                        ) {
                                            val err = t.message
                                            Log.i("teste login", err.toString())
                                        }
                                    }
                                    )
//                                    var genres by remember {
//                                        mutableStateOf(listOf<Genre>())
//                                    }
                                },
                                modifier = Modifier
                                    .width(160.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_purple))


                            ) {
                                Text(
                                    text = stringResource(id = R.string.login_name),
                                    color = colorResource(
                                        id = R.color.white
                                    ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            if(!emailValue.isEmpty()) {
                                emailErrorRequiredInput = false
                                colorIconEmail = colorResource(id = R.color.eulirio_purple_text_color_border)
                            }

                            if(!passwordValue.isEmpty()) {
                                passwordErrorRequiredInput = false
                                colorIconPassword = colorResource(id = R.color.eulirio_purple_text_color_border)
                            }

                            Text(
                                modifier = Modifier
                                    .padding(top = 7.dp),
                                text = stringResource(id = R.string.does_have_account),
                                color = colorResource(id = R.color.black),
                                fontSize = 12.sp,
                            )
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        val intent = Intent(context, RegisterPage::class.java)
                                        context.startActivity(intent)
                                    },
                                text = stringResource(id = R.string.sign_up_now),
                                color = colorResource(id = R.color.eulirio_purple_text_color),
                                fontSize = 14.sp,
                            )
                        }

                    }

                }

            }

        }

    }

}


//@Preview(showBackground = true)
//@Composable
//fun LoginPagePreview() {
//    LoginPagePreview()
//}