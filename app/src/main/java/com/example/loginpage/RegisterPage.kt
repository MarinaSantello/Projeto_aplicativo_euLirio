package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.ui.theme.LoginPageTheme
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AccountCircle
import com.example.loginpage.API.user.CallAPI
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    registerPage()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun registerPage() {

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    var userValue by rememberSaveable{
        mutableStateOf("")
    }

    var emailValue by rememberSaveable {
        mutableStateOf("")
    }

    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }

    var confirmPasswordValue by rememberSaveable{
        mutableStateOf("")
    }
    var showConfirmPassword by remember {
        mutableStateOf(false)
    }

    var userErrorRequiredInput by remember {
        mutableStateOf(false)
    }
    var emailErrorRequiredInput by remember {
        mutableStateOf(false)
    }
    var passwordErrorRequiredInput by remember {
        mutableStateOf(false)
    }
    var passwordErrorMinCharacter by remember {
        mutableStateOf(false)
    }
    var confirmPasswordErrorMinCharacter by remember {
        mutableStateOf(false)
    }
    var confirmPasswordErrorRequiredInput by remember {
        mutableStateOf(false)
    }

    var invalidEmail by remember {
        mutableStateOf(false)
    }
    var invalidUser by remember {
        mutableStateOf(false)
    }
    var usernameInvalid by remember {
        mutableStateOf(true)
    }

    var clickButton by remember {
        mutableStateOf(false)
    }

    var colorIconUser = colorResource(id = R.color.eulirio_purple_text_color_border)
    var colorIconEmail = colorResource(id = R.color.eulirio_purple_text_color_border)
    var colorIconPassword = colorResource(id = R.color.eulirio_purple_text_color_border)
    var colorIconConfirmPassword = colorResource(id = R.color.eulirio_purple_text_color_border)

    val userFocusRequester = remember {
        FocusRequester()
    }
    val emailFocusRequester = remember {
        FocusRequester()
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val confirmPasswordFocusRequester = remember {
        FocusRequester()
    }

    //userFocusRequester.requestFocus()

    val emailVerify = emailValue.split('@')

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
                                value = userValue,
                                onValueChange = {
                                    userValue = it;
                                },

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp)
                                    .focusRequester(userFocusRequester),
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(fontSize = 16.sp),

                                label = {
                                    Text(
                                        text = stringResource(id = R.string.user_name),
                                        style = TextStyle (fontWeight = FontWeight.Light)
                                    )
                                },

                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Icone de e-mail",
                                        modifier = Modifier.height(24.dp),
                                        tint = colorIconUser
                                    )
                                },

                                isError = false,

                                keyboardOptions =  KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )

                            if (invalidUser) Text(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 3.dp),
                                text = stringResource(id = R.string.erro_message_invalid_user),
                                color = Color(0xFFB00020),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = emailValue,
                                onValueChange = {
                                    emailValue = it;
                                },

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp)
                                    .focusRequester(emailFocusRequester),
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(fontSize = 16.sp),

                                label = {
                                    Text(
                                        text = stringResource(id = R.string.email_name),
                                        style = TextStyle (fontWeight = FontWeight.Light)
                                    )
                                },

                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Email,
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

                            if (invalidEmail) Text(
                                    modifier = Modifier
                                        .padding(top = 3.dp)
                                        .padding(start = 16.dp, end = 16.dp, top = 3.dp),
                                    text = stringResource(id = R.string.erro_message_invalid_email),
                                    color = Color(0xFFB00020),
                                    fontSize = 12.sp,
                                textAlign = TextAlign.Center
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
                                        Icons.Outlined.Lock,
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
                                    imeAction = ImeAction.Next
                                ),

                                singleLine = true
                            )

                            if (clickButton && passwordErrorMinCharacter) Text(
                                modifier = Modifier
                                    .padding(top = 3.dp),
                                text = stringResource(id = R.string.FirebaseAuthWeakPasswordException),
                                color = Color(0xFFB00020),
                                fontSize = 12.sp,
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = confirmPasswordValue,
                                onValueChange = {
                                    confirmPasswordValue = it;
                                },

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp)
                                    .focusRequester(confirmPasswordFocusRequester),
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(fontSize = 16.sp),

                                label = {
                                    Text(
                                        text = stringResource(id = R.string.password_name_confirm),
                                        style = TextStyle (fontWeight = FontWeight.Light)
                                    )
                                },

                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Lock,
                                        contentDescription = "Icone de cadeado",
                                        modifier = Modifier.height(24.dp),
                                        tint = colorIconConfirmPassword
                                    )
                                },

                                trailingIcon = {
                                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                        Icon(
                                            modifier = Modifier.height(16.dp),
                                            contentDescription = if (showConfirmPassword) "Show Password" else "Hide Password",
                                            imageVector = if (showConfirmPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            tint = Color(0xFF010101)
                                        )
                                    }
                                },

                                isError = confirmPasswordErrorRequiredInput,

                                singleLine = true,

                                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),

                                keyboardOptions =  KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions (
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                            )

                            if (clickButton && confirmPasswordErrorMinCharacter) Text(
                                modifier = Modifier
                                    .padding(top = 3.dp),
                                text = stringResource(id = R.string.FirebaseAuthWeakPasswordException),
                                color = Color(0xFFB00020),
                                fontSize = 12.sp,
                            )

                            if (clickButton && (userValue.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty() || confirmPasswordValue.isEmpty())) Text(
                                    modifier = Modifier
                                        .padding(top = 5.dp),
                                    text = stringResource(id = R.string.erro_message_input_required),
                                    color = Color(0xFFB00020),
                                    fontSize = 14.sp,
                                )

                            Button(
                                onClick = {
                                    clickButton = true
//                                    CallAPI.verifyUsername(userValue) {
//                                        usernameInvalid = it
//                                    }
//
                                    if(userValue.isEmpty()) {
                                        userErrorRequiredInput = true
                                        colorIconUser = Color(0xFFB00020)
                                        userFocusRequester.requestFocus()
                                    }
//                                    else if (usernameInvalid) {
//                                        userErrorRequiredInput = true
//                                        colorIconUser = Color(0xFFB00020)
//                                        userFocusRequester.requestFocus()
//                                        Toast.makeText(context, "Este nome de usuário já existe. Por favor utilize um único.", Toast.LENGTH_SHORT)
//                                            .show()
//                                    }
                                    else userErrorRequiredInput = false

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

                                    if(passwordValue.length < 6) {
                                        passwordErrorRequiredInput = true
                                        passwordErrorMinCharacter = true
                                        colorIconPassword = Color(0xFFB00020)
                                        passwordFocusRequester.requestFocus()
                                    }
                                    else passwordErrorRequiredInput = false

                                    if(confirmPasswordValue.isEmpty()) {
                                        confirmPasswordErrorRequiredInput = true
                                        colorIconConfirmPassword = Color(0xFFB00020)
                                        confirmPasswordFocusRequester.requestFocus()
                                    }
                                    else confirmPasswordErrorRequiredInput = false

                                    if(confirmPasswordValue.length < 6) {
                                        confirmPasswordErrorRequiredInput = true
                                        confirmPasswordErrorMinCharacter = true
                                        colorIconConfirmPassword = Color(0xFFB00020)
                                        confirmPasswordFocusRequester.requestFocus()
                                    }
                                    else confirmPasswordErrorRequiredInput = false

                                    if (passwordValue != confirmPasswordValue) {
                                        confirmPasswordErrorRequiredInput = true
                                        colorIconConfirmPassword = Color(0xFFB00020)
                                        confirmPasswordFocusRequester.requestFocus()

                                        Toast.makeText(
                                            context,
                                            "Insira senhas iguais, por favor.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    if (' ' in emailValue || '@' !in emailValue || emailVerify[0].isEmpty() || emailVerify[1].isEmpty() || "." !in emailVerify[1]) {
                                        emailErrorRequiredInput = true
                                        colorIconEmail = Color(0xFFB00020)
                                        emailFocusRequester.requestFocus()

                                        invalidEmail = true
                                    }

                                    if (' ' in userValue) {
                                        userErrorRequiredInput = true
                                        colorIconUser = Color(0xFFB00020)
                                        userFocusRequester.requestFocus()

                                        invalidUser = true
                                    }

                                    if (!userErrorRequiredInput && !emailErrorRequiredInput && !passwordErrorRequiredInput && !confirmPasswordErrorRequiredInput) {
                                        accounValidate(emailValue, confirmPasswordValue, userValue, context)

                                        invalidEmail = false
                                        invalidUser = false
                                    }
                                },
                                modifier = Modifier
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

                            if(userValue.isNotEmpty() && ' ' !in userValue && !usernameInvalid) {
                                userErrorRequiredInput = false
                                invalidUser = false
                                colorIconUser = colorResource(id = R.color.eulirio_purple_text_color_border)
                            }

                            if(emailValue.isNotEmpty() && ' ' !in emailValue && '@' in emailValue && emailVerify[0].isNotEmpty() && emailVerify[1].isNotEmpty() && ".com" in emailVerify[1]) {
                                emailErrorRequiredInput = false
                                invalidEmail = false
                                colorIconEmail = colorResource(id = R.color.eulirio_purple_text_color_border)
                            }

                            if(passwordValue.isNotEmpty() && passwordValue.length >= 6) {
                                passwordErrorRequiredInput = false
                                passwordErrorMinCharacter = false
                                colorIconPassword = colorResource(id = R.color.eulirio_purple_text_color_border)
                            }

                            if(confirmPasswordValue.isNotEmpty() && passwordValue == confirmPasswordValue && confirmPasswordValue.length >= 6) {
                                confirmPasswordErrorRequiredInput = false
                                confirmPasswordErrorMinCharacter = false
                                colorIconConfirmPassword = colorResource(id = R.color.eulirio_purple_text_color_border)
                            }
                        }
                    }
                }
            }

        }

    }
}



fun accounValidate(email: String, password: String, user: String, context: Context) {

    // obtendo uma instancia do firebase auth
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { it -> // retorna o resultado da autenticacao, quando completada com sucesso
            val intent = Intent(context, RegisterPageSecondPart::class.java)

            intent.putExtra("user1", user)
            intent.putExtra("email1", email)
            intent.putExtra("senha1", password)

            context.startActivity(intent)
        }
        .addOnFailureListener { // retorna o resultado da autenticacao, quando ela falha
            try {
                throw it
            }
            catch (error: FirebaseAuthUserCollisionException) { // caso o usuario tente cadastrar um login que já existe
                Toast.makeText(context, R.string.FirebaseAuthUserCollisionException, Toast.LENGTH_SHORT).show()
            }
            catch (error: FirebaseAuthInvalidUserException) { // senha fraca
                Toast.makeText(context, R.string.FirebaseAuthInvalidUserException, Toast.LENGTH_SHORT).show()
            }
            catch (error: FirebaseAuthInvalidCredentialsException){ // usuario/email invalido
                Toast.makeText(context, R.string.FirebaseAuthInvalidCredentialsException, Toast.LENGTH_SHORT).show()

            }
            catch (error: FirebaseAuthException) { // erro generico
                Toast.makeText(context, R.string.FirebaseAuthException, Toast.LENGTH_SHORT).show()
            }
        }
}
@Preview(showBackground = true)
@Composable
fun registerPagePreview() {
    registerPagePreview()
}
