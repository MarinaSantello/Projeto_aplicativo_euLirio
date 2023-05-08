package com.example.loginpage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.Commit
import com.example.loginpage.ui.theme.LoginPageTheme
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.ceil

class PostCommit : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PostCommitPage(rememberNavController())
                }
            }
        }
    }
}

@Composable
fun PostCommitPage(
    navController: NavController
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    var commit = remember {
        mutableStateOf<Commit?>(null)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBarCommit(navController, commit, userID.idUser) }
    ) {it
        CommitData()
    }

}

@Composable
fun CommitData (
) {
    var titleState by remember {
        mutableStateOf("")
    }
    var rating by remember {
        mutableStateOf(0)
    }
    var avaliacaoState by remember {
        mutableStateOf("")
    }
    var switchCheckedState by remember {
        mutableStateOf(false)
    }

    //Focus Requesters
    val titleFocusRequester = remember{
        FocusRequester()
    }
    val sinopseFocusRequester = remember{
        FocusRequester()
    }

    val filledStars = floor(rating.toDouble()).toInt()
    val unfilledStars = (5 - ceil(rating.toDouble())).toInt()

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp, 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TextField(
                value = titleState,
                onValueChange = { titleState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(titleFocusRequester),
                label = {
                    Text(
                        text = stringResource(id = R.string.publicartitulo),
                        color = colorResource(id = R.color.eulirio_purple_text_color_border),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    backgroundColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    unfocusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border)
                ),
//                trailingIcon = {
//                    Icon(
//                        Icons.Outlined.Error, contentDescription = "",
//                        modifier = Modifier.size(15.dp),
//                        tint = if(checkTitle){
//                            Color.Red
//                        }else{
//                            colorResource(id = R.color.eulirio_purple_text_color_border)
//                        }
//                    )
//                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = avaliacaoState,
            onValueChange = { avaliacaoState = it },
            modifier = Modifier
                .heightIn(160.dp)
                .fillMaxWidth()
                .focusRequester(sinopseFocusRequester),
            label = {
                Text(
                    text = stringResource(id = R.string.avaliacaopub),
                    color = colorResource(id = R.color.eulirio_purple_text_color_border),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            },
            singleLine = false,
//                isError = checkSinopse,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                backgroundColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                unfocusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Switch(
            checked = switchCheckedState,
            onCheckedChange = { switchCheckedState = it }
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Switch(
                checked = switchCheckedState,
                onCheckedChange = { switchCheckedState = it },
                modifier = Modifier.semantics { contentDescription = "Demo with icon" },
                /*
                The "checkedIconColor" and "uncheckedIconColor" excluded from this list
                because you need to set an icon first, use the "thumbContent" parameter.
                You can find an example in the next section.
                 */
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    checkedTrackColor = Color.LightGray,
//                    checkedBorderColor = Color.Green,
                    uncheckedThumbColor = Color.Red,
                    uncheckedTrackColor = Color.LightGray,
//                    uncheckedBorderColor = Color.Red,
                    disabledCheckedThumbColor = Color.Green.copy(alpha = ContentAlpha.disabled),
                    disabledCheckedTrackColor = Color.LightGray.copy(alpha = ContentAlpha.disabled),
//                    disabledCheckedBorderColor = Color.Green.copy(alpha = ContentAlpha.disabled),
                    disabledUncheckedThumbColor = Color.Red.copy(alpha = ContentAlpha.disabled),
                    disabledUncheckedTrackColor = Color.LightGray.copy(alpha = ContentAlpha.disabled),
//                    disabledUncheckedBorderColor = Color.Red.copy(alpha = ContentAlpha.disabled),
                )
            )
            Row() {
                repeat(filledStars) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                Log.i("estrela", it.toString())
                                rating = it + 1
                            },
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }
                repeat(unfilledStars) {
                    Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                Log.i("estrela", it.toString())
                                rating += it + 1
                            },
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarCommit(
    navController: NavController,
    commit: MutableState<Commit?>,
    userID: Int
) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo_icone_eulirio),
                contentDescription = "logo aplicativo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 56.dp),
                alignment = Alignment.Center
            )
        },
                    navigationIcon = {
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
                                    .fillMaxSize(.8f)
                                    .padding(start = 12.dp)
                                    .clip(RoundedCornerShape(100.dp)),
                                tint = colorResource(id = R.color.eulirio_black)
                            )
                        }
                    },
        backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
        elevation = 0.dp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    LoginPageTheme {
        PostCommitPage(rememberNavController())
    }
}