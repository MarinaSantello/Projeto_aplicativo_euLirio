package com.example.loginpage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.recommendation.CallRecommendationAPI
import com.example.loginpage.models.Recommendation
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.MontSerratBold
import com.example.loginpage.ui.theme.QuickSand
import com.example.loginpage.ui.theme.SpartanExtraLight
import kotlinx.coroutines.launch

class PostRecommendation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PostRecommendationPage(53, rememberNavController())
                }
            }
        }
    }
}

@Composable
fun PostRecommendationPage(
    idAnnouncement: Int,
    navController: NavController
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val userID = UserIDrepository(context).getAll()[0].idUser

    val resenhaState = remember {
        mutableStateOf("")
    }
    val switchCheckedState = remember {
        mutableStateOf(false)
    }

    var recommendation = remember {
        mutableStateOf<Recommendation?>(null)
    }

    recommendation.value = Recommendation(
        conteudo = resenhaState.value,
        userID = userID,
        anuncioID = idAnnouncement,
        spoiler = switchCheckedState.value.toString()
    )

    var announcement by remember {
        mutableStateOf<AnnouncementGet?>(null)
    }

    CallAnnouncementAPI.getAnnouncement(idAnnouncement, userID) {
        announcement = it
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBarRecommendation(navController, recommendation) }
    ) {it
        RecommendationData(resenhaState, switchCheckedState)//, announcement!!)
    }

}

@Composable
fun TopBarRecommendation (
    navController: NavController,
    recommendation: MutableState<Recommendation?>
) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            navController.popBackStack()
                        }
                    }
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = "botao para fechar a página",
                        modifier = Modifier
                            .fillMaxHeight()
//                        .padding(start = 12.dp)
                            .clip(RoundedCornerShape(100.dp)),
                        tint = colorResource(id = R.color.eulirio_black)
                    )
                }


                Button(
                    onClick = {
//                        if(!emptySpaceTitle.value && !maxSpaceTitle.value && !emptySpaceResenha.value && !maxSpaceResenha.value && !emptySpaceResenha.value){
                            if (recommendation.value != null) CallRecommendationAPI.postRecommendation(
                                recommendation.value!!
                            ) {
                                Log.i("resposta api commit", it.toString())
                                if (it == 201) navController.popBackStack()
                            }
//                        }
                    },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .height(32.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "PUBLICAR",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        },
        backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
        elevation = 0.dp
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecommendationData(
    resenhaState: MutableState<String>,
    switchCheckedState: MutableState<Boolean>,
//    announcement: AnnouncementGet
) {
    val resenhaFocusRequester = remember{
        FocusRequester()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Solicitar o foco assim que o compositor estiver pronto
    LaunchedEffect(Unit) {
        resenhaFocusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(12.dp, 8.dp)
    ) {
        Text(
            text = "Recomende essa obra",
            fontFamily = MontSerratBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.eulirio_black)
        )

        TextField(
            value = resenhaState.value,
            onValueChange = { resenhaState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(280.dp)
                .focusRequester(resenhaFocusRequester),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.recomendacaopub),
                    color = Color(0x801E1E1E),
                    fontFamily = QuickSand,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 16.sp
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Executar ação ao pressionar "Done" no teclado
                    keyboardController?.hide()
                }
            ),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            colors =
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
            ) {

            Row(
                modifier = Modifier
                    .padding(bottom = 240.dp)
                    .clickable { switchCheckedState.value = !switchCheckedState.value },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = switchCheckedState.value,
                    onCheckedChange = { switchCheckedState.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        checkedTrackColor = Color.LightGray,
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = Color.LightGray,
                    )
                )

                Text(
                    text = "Spoilers",
                    fontFamily = SpartanExtraLight
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    LoginPageTheme {
//        PostRecommendationPage(0)
    }
}