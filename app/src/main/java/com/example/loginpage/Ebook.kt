package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.ui.theme.*
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

//class ViewEbook : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    EbookView(1)
//                }
//            }
//        }
//    }
//}

@Composable
fun EbookView(idAnnouncement: Int) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    //val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!fabState.value)
                            fabState.value = !fabState.value
                    }
                )
            },
        scaffoldState = scaffoldState,
        topBar = { TopBarEbook(scaffoldState, topBarState, context, false) },
        bottomBar = { BottomBarEbook(bottomBarState, false, context) },
    ) {
        ShowEbook(idAnnouncement, false, it.calculateBottomPadding(), context)
    }
}

@Composable
fun ShowEbook(
    idAnnouncement: Int,
    userAuthor: Boolean,
    bottomBarLength: Dp,
    context: Context
) {
    CallAnnouncementAPI.getAnnouncement(idAnnouncement) {
        val announcementGet = it
    }

    var likeState by remember {
        mutableStateOf(false)
    }

    var saveState by remember {
        mutableStateOf(false)
    }

    var viewState by remember {
        mutableStateOf(false)
    }

    var pageState by remember {
        mutableStateOf(false)
    }

    var vendaState by remember {
        mutableStateOf(false)
    }

    var volumeState by remember {
        mutableStateOf(false)
    }

    var postState by remember {
        mutableStateOf(false)
    }

    var sinopseState by remember {
        mutableStateOf("")
    }

    var reviewState by remember {
        mutableStateOf("")
    }
    var selectedDate by remember {
        mutableStateOf("")
    }

    //Card de informações do usuario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Divider(
            thickness = .5.dp,
            color = Color.White
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(168.dp),
            shape = RoundedCornerShape(
                bottomEnd = if (userAuthor) 40.dp else 0.dp,
                bottomStart = if (userAuthor) 40.dp else 0.dp
            ),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 0.dp
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(28.dp, 12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                    contentDescription = "capa do livro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(98.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Column() {

                        Text(
                            text = "Lorem Ipsum",
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Light,
                            fontFamily = SpartanBold,
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.eulirio_black)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        val generos = listOf<String>("genero 1", "genero 2", "genero 4")

                        LazyRow() {
                            items(generos) {
                                Card(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp),
                                    backgroundColor = Color(0xFF1B0C36),
                                    shape = RoundedCornerShape(100.dp),
                                ) {
                                    Text(
                                        text = it,//.nome.uppercase(),
                                        fontSize = 10.sp,
                                        fontFamily = MontSerratSemiBold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(12.dp, 1.dp),
                                        color = Color.White
                                    )

                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        //Sistema de avaliação
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Outlined.StarOutline,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(text = "(4,5)")

                        }
                    }

                    Row() {
                        //Coluna de curtir
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário curtiu a publicação
                                if (likeState) {
                                    Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = "icone de curtir",
                                        modifier = Modifier
                                            .clickable { likeState = !likeState },
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                    )
                                } else Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = "icone de curtir",
                                    modifier = Modifier
                                        .clickable { likeState = !likeState },
                                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                )

                                Text(
                                    text = "curtidas",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = "570",
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        //Coluna de favoritar
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário favoritou a publicação
                                if (saveState) {
                                    Icon(
                                        Icons.Outlined.Bookmark,
                                        contentDescription = "icone de salvar",
                                        modifier = Modifier
                                            .clickable { saveState = !saveState },
                                        tint = Color.White
                                    )
                                } else Icon(
                                    Icons.Outlined.BookmarkAdd,
                                    contentDescription = "icone de salvar",
                                    modifier = Modifier
                                        .clickable { saveState = !saveState },
                                    tint = Color.White
                                )

                                Text(
                                    text = "favoritos",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = "182",
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        //Coluna de marcar como lido
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário marcou como lido a publicação
                                if (viewState) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "icone de salvar",
                                        modifier = Modifier
                                            .clickable { viewState = !viewState },
                                        tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                    )
                                } else Icon(
                                    Icons.Outlined.CheckCircle,
                                    contentDescription = "icone de salvar",
                                    modifier = Modifier
                                        .clickable { viewState = !viewState },
                                    tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                )

                                Text(
                                    text = "lidos",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = "4,1k",
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        if (!userAuthor) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp),
                elevation = 0.dp
            ) {
                Row(Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .fillMaxHeight(),
                        backgroundColor = Color(0xDFBDB5A),
                        shape = RoundedCornerShape(bottomStart = 40.dp),
                        border = BorderStroke(
                            .5.dp,
                            colorResource(id = R.color.eulirio_yellow_card_background)
                        ),
                        elevation = 0.dp
                    ) {
                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "R$ 20,00",
                                modifier = Modifier.fillMaxWidth(),
                                color = colorResource(id = R.color.eulirio_yellow_card_background),
                                textAlign = TextAlign.Center,
                                fontSize = 28.sp,
                                fontFamily = Spartan
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                Toast
                                    .makeText(context, "oi", Toast.LENGTH_SHORT)
                                    .show()
                            },
                        shape = RoundedCornerShape(0.dp),
                        backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                        elevation = 0.dp
                    ) {
                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.ebook_buy).uppercase(),
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraLight,
                                style = MaterialTheme.typography.h2
                            )
                        }
                    }
                }
            }
        }

        Column(Modifier.height(1000.dp)) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                    contentDescription = "foto da pessoa",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(12.dp)
                        .clip(RoundedCornerShape(12.dp)

                        )
                )

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                Column() {
                    Text(
                        text = "Noah Sebastian",
                        fontSize = 12.sp
                    )
                    Text(
                        text = "@n.sebastian",
                        fontSize = 8.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Spacer(modifier = Modifier.height(20.dp))

            //row dos icones
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                //coluna de páginas
                Column() {

                    Icon(
                        Icons.Outlined.Description,
                        contentDescription = "icone de páginas",
                        modifier = Modifier
                            .clickable { pageState = !pageState },
                        tint = Color.Black
                    )
                    Text(
                        text = "páginas",
                        fontSize = 10.sp,
                        fontFamily = QuickSand,
                        fontWeight = FontWeight.W500,

                        )
                    Text(
                        text = "41",
                        fontSize = 20.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier = Modifier.width(15.dp))

                //coluna de volumes
                Column() {

                    Icon(
                        Icons.Outlined.LibraryBooks,
                        contentDescription = "icone de volume",
                        modifier = Modifier
                            .clickable { volumeState = !volumeState },
                        tint = Color.Black
                    )
                    Text(
                        text = "volume",
                        fontSize = 10.sp,
                        fontFamily = QuickSand,
                        fontWeight = FontWeight.W500,
                    )
                    Text(
                        text = "2",
                        fontSize = 20.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                //coluna de vendas
                Column() {

                    Icon(
                        Icons.Outlined.ShoppingBag,
                        contentDescription = "icone de vendas",
                        modifier = Modifier
                            .clickable { vendaState = !vendaState },
                        tint = Color.Black
                    )

                    Text(
                        text = "vendas",
                        fontSize = 10.sp,
                        fontFamily = QuickSand,
                        fontWeight = FontWeight.W500,

                        )
                    Text(
                        text = "1,2k",
                        fontSize = 20.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold
                    )


                }

                Spacer(modifier = Modifier.width(15.dp))

                //coluns de publicaçao
                Column() {

                    Icon(
                        Icons.Outlined.CalendarMonth,
                        contentDescription = "icone de publicacoes",
                        modifier = Modifier
                            .clickable { postState = !postState },
                        tint = Color.Black
                    )

                    Text(
                        text = "publicação",
                        fontSize = 10.sp,
                        fontFamily = QuickSand,
                        fontWeight = FontWeight.W500,

                        )
                    Text(
                        text = "12 Jun.2022",
                        fontSize = 12.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //sinopse
        Column(Modifier.fillMaxSize()) {
//                    Divider(
//                        color = Color.Black,
//                        thickness = 1.dp,
//                        modifier = Modifier.fillMaxWidth()
//                    )
            Text(text = "Classificação indicativa: Livre")
            Text(text = "Disponível em: PDB e PUB")
        }

    }
    Divider(
        color = Color.Black,
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )

    @Composable
    fun Avaliacoes(context: Context) {

        // column principal
        Column(modifier = Modifier.fillMaxSize()) {
            Card() {
                Row() {
                    Icon(
                        Icons.Outlined.ChatBubble,
                        contentDescription = "conversa",
                        tint = Color.Black
                    )
                    Text(
                        text = "Avaliações do livro (2)",
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = { },
                    ) {
                        Text(text = "AVALIAR")
                    }
                }

                //perfil
                Row() {
                    Image(
                        painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                        contentDescription = "foto da pessoa",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Column() {
                        //estrelas

                        Text(text = "@n.sebastian")
                    }

                }

                Text(
                    text = stringResource(id = R.string.resenhatilte)
                )
                Text(
                    text = stringResource(id = R.string.review)
                )

            }
        }




    }
}


@Composable
fun BottomBarEbook(
    bottomBarState: MutableState<Boolean>,
    userAuthor: Boolean,
    context: Context
) {
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            if (userAuthor) {
                BottomAppBar(
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "oi", Toast.LENGTH_SHORT).show()
                    },
                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Text(
                        text = stringResource(R.string.ebook_edit).uppercase(),
                        modifier = Modifier
                            .fillMaxWidth(),
//                                .padding(end = 44.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        style = MaterialTheme.typography.h2
                    )
                }
            } else {
                BottomAppBar(
                    contentPadding = PaddingValues(0.dp),
                    elevation = 0.dp
//                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Row(Modifier.fillMaxSize()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .fillMaxHeight()
                                .clickable {
                                    Toast
                                        .makeText(context, "oi", Toast.LENGTH_SHORT)
                                        .show()
                                },
                            shape = RoundedCornerShape(0.dp),
                            backgroundColor = Color.White,
                            elevation = 0.dp
                        ) {
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.ebook_bag).uppercase(),
                                    color = colorResource(id = R.color.eulirio_yellow_card_background),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    style = MaterialTheme.typography.h2
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    Toast
                                        .makeText(context, "oi", Toast.LENGTH_SHORT)
                                        .show()
                                },
                            shape = RoundedCornerShape(0.dp),
                            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                            elevation = 0.dp
                        ) {
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.ebook_buy).uppercase(),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    style = MaterialTheme.typography.h2
                                )
                            }
                        }
                    }
                }
            }
        }
    )

}

@Composable
fun TopBarEbook(
    scaffoldState: ScaffoldState,
    topBarState: MutableState<Boolean>,
    context: Context,
    userAuthor: Boolean
) {

    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = topBarState.value,
        modifier = Modifier.background(Color.Red),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.title_ebook).uppercase(),
                            modifier = Modifier
                                .fillMaxWidth(.8f),
//                                .padding(end = 44.dp),
                            color = colorResource(id = R.color.eulirio_black),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2
                        )

                        Icon(
                            Icons.Rounded.MoreVert,
                            contentDescription = "botao de menu",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .fillMaxHeight()
                                .width(32.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .clickable { },
                            tint = if (userAuthor) Color.Transparent else colorResource(
                                id = R.color.eulirio_black
                            )
                        )

                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val intent = Intent(context, Home::class.java)
                                context.startActivity(intent)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Rounded.ChevronLeft,
                            contentDescription = "botao para voltar",
                            modifier = Modifier
                                .fillMaxSize(.8f)
                                .padding(start = 12.dp)
                                .clip(RoundedCornerShape(100.dp)),
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                    }
                },
                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                elevation = 0.dp
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    LoginPageTheme {
        EbookView(1)
    }
}