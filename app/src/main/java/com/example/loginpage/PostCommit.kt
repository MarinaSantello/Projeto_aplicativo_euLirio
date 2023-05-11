package com.example.loginpage

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginpage.API.comment.CallCommentAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.Commit
import com.example.loginpage.models.CommitSS
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.MontSerratBold
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.ceil

//class PostCommit : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    PostCommitPage(rememberNavController(), 62)
//                }
//            }
//        }
//    }
//}

var emptySpaceTitle: MutableState<Boolean> = mutableStateOf(false)
var maxSpaceTitle: MutableState<Boolean> = mutableStateOf(false)
var emptySpaceResenha: MutableState<Boolean> = mutableStateOf(false)
var maxSpaceResenha: MutableState<Boolean> = mutableStateOf(false)
var emptySpaceAvaliaton: MutableState<Boolean> = mutableStateOf(false)

@Composable
fun PostCommitPage(
    navController: NavController,
    announcementID: Int
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    var titleState = remember {
        mutableStateOf("")
    }
    var rating = remember {
        mutableStateOf(0)
    }
    var avaliacaoState = remember {
        mutableStateOf("")
    }
    var switchCheckedState = remember {
        mutableStateOf(false)
    }

    var commit = remember {
        mutableStateOf<Commit?>(null)
    }

    commit.value = Commit(
        id = null,
        titulo = titleState.value,
        resenha = avaliacaoState.value,
        avaliacao = rating.value,
        spoiler = switchCheckedState.value.toString(),
        userID = userID.idUser,
        announcementID = announcementID
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBarCommit(navController, commit) }
    ) {it
        CommitData(titleState, rating, avaliacaoState, switchCheckedState)
    }

}

@Composable
fun CommitData (
    titleState: MutableState<String>,
    rating: MutableState<Int>,
    avaliacaoState: MutableState<String>,
    switchCheckedState: MutableState<Boolean>,
) {
    //Focus Requesters
    val titleFocusRequester = remember{
        FocusRequester()
    }
    val sinopseFocusRequester = remember{
        FocusRequester()
    }

    val filledStars = floor(rating.value.toDouble()).toInt()
    val unfilledStars = (5 - ceil(rating.value.toDouble())).toInt()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 8.dp)
    ) {
        Text(
            text = "Recomende uma obra",
            fontFamily = MontSerratBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.eulirio_black)
        )
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TextField(
                value = titleState.value,
                onValueChange = { titleState.value = it },
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
            value = avaliacaoState.value,
            onValueChange = { avaliacaoState.value = it },
            modifier = Modifier
                .heightIn(240.dp)
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
        
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                repeat(filledStars) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                Log.i("estrela", it.toString())
                                rating.value = it + 1
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
                                rating.value += it + 1
                            },
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }
            }
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
                )
            }
        }
    }
}

@Composable
fun TopBarCommit(
    navController: NavController,
    commit: MutableState<Commit?>
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
                        if(commit.value!!.titulo == null){
                            emptySpaceTitle.value = true
                        }else{
                            emptySpaceTitle.value = false
                        }

                        if(commit.value!!.titulo.length > 80){
                            maxSpaceTitle.value = true
                        }else{
                            maxSpaceTitle.value = false
                        }

                        if(commit.value!!.resenha == null){
                            emptySpaceResenha.value = true
                        }else{
                            emptySpaceResenha.value = false
                        }

                        if(commit.value!!.resenha.length > 2000){
                            maxSpaceResenha.value = true
                        }else{
                            maxSpaceResenha.value = false
                        }

                        if(commit.value!!.avaliacao == null){
                            emptySpaceAvaliaton.value = true
                        }else{
                            emptySpaceAvaliaton.value = false
                        }

                        if (commit.value != null) CallCommentAPI.postComment(commit.value!!) {
                            Log.i("resposta api commit", it.toString())
                            if (it == 200) navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .height(32.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(text = "PUBLICAR")
                }
            }
        },
        backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
        elevation = 0.dp
    )
}


@Composable
fun PostCommitPageSS(
    navController: NavController,
    shortStoryID: Int
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    var titleState = remember {
        mutableStateOf("")
    }
    var rating = remember {
        mutableStateOf(0)
    }
    var avaliacaoState = remember {
        mutableStateOf("")
    }
    var switchCheckedState = remember {
        mutableStateOf(false)
    }

    var commit = remember {
        mutableStateOf<CommitSS?>(null)
    }

    commit.value = CommitSS(
        id = null,
        titulo = titleState.value,
        resenha = avaliacaoState.value,
        avaliacao = rating.value,
        spoiler = switchCheckedState.value.toString(),
        userID = userID.idUser,
        shortStoryID = shortStoryID
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBarCommitSS(navController, commit) }
    ) {it
        CommitDataSS(titleState, rating, avaliacaoState, switchCheckedState)
    }

}

@Composable
fun CommitDataSS (
    titleState: MutableState<String>,
    rating: MutableState<Int>,
    avaliacaoState: MutableState<String>,
    switchCheckedState: MutableState<Boolean>,
) {
    //Focus Requesters
    val titleFocusRequester = remember{
        FocusRequester()
    }
    val sinopseFocusRequester = remember{
        FocusRequester()
    }

    val filledStars = floor(rating.value.toDouble()).toInt()
    val unfilledStars = (5 - ceil(rating.value.toDouble())).toInt()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 8.dp)
    ) {
        Text(
            text = "Recomende uma obra",
            fontFamily = MontSerratBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.eulirio_black)
        )
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TextField(
                value = titleState.value,
                onValueChange = { titleState.value = it },
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
            value = avaliacaoState.value,
            onValueChange = { avaliacaoState.value = it },
            modifier = Modifier
                .heightIn(240.dp)
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

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                repeat(filledStars) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                Log.i("estrela", it.toString())
                                rating.value = it + 1
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
                                rating.value += it + 1
                            },
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }
            }
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
                )
            }
        }
    }
}

@Composable
fun TopBarCommitSS(
    navController: NavController,
    commit: MutableState<CommitSS?>
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

//                Image(
//                    painter = painterResource(id = R.drawable.logo_icone_eulirio),
//                    contentDescription = "logo aplicativo",
////                    modifier = Modifier
////                        .fillMaxWidth(.8f),
//                    alignment = Alignment.Center
//                )

                Button(
                    onClick = {
                        if (commit.value != null) CallCommentAPI.postCommentSS(commit.value!!) {
                            Log.i("resposta api commit", it.toString())
                            if (it == 200) navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .height(32.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(text = "PUBLICAR")
                }
            }
        },
//                    navigationIcon = {
//                    },
        backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
        elevation = 0.dp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    LoginPageTheme {
//        PostCommitPage(rememberNavController())
    }
}