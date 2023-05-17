package com.example.loginpage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.recommendation.CallRecommendationAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.LikeAnnouncement
import com.example.loginpage.models.Recommendation
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.ui.theme.*
import kotlin.math.ceil
import kotlin.math.floor

class RecommendationPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowRecommendation(rememberNavController(), 7)
                }
            }
        }
    }
}

@Composable
fun ShowRecommendation(
    navController: NavController,
    idRecommendation: Int
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }
    var userAuthor = remember { mutableStateOf(false) }

    val userID = UserIDrepository(context).getAll()[0].idUser

    var recommendation by remember {
        mutableStateOf<Recommendation?>(null)
    }

    var idAnnouncement by remember { mutableStateOf(0) }

    CallRecommendationAPI.getRecommendationByID(idRecommendation, userID) {
        recommendation = it

        idAnnouncement = it.anuncioID
    }

    var announcement by remember {
        mutableStateOf<AnnouncementGet?>(null)
    }

    if (recommendation != null) CallAnnouncementAPI.getAnnouncement(idAnnouncement, userID) {
        announcement = it

        userAuthor.value = (it.usuario[0].idUsuario == userID)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "RECOMENDAÇÃO",
                            modifier = Modifier
                                .fillMaxWidth(.82f),
//                                .padding(end = 44.dp),
                            color = colorResource(id = R.color.eulirio_black),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2,
                            fontSize = 20.sp
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
                            tint = colorResource(id = R.color.eulirio_black)
                        )

                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
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
                backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
                elevation = 0.dp
            )
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = colorResource(id = R.color.eulirio_beige_color_background)
            ) {

                var likeState by remember {
                    mutableStateOf(recommendation!!.curtido)
                }

                var saveState by remember{
                    mutableStateOf(recommendation!!.favorito)
                }

                var quantidadeLikesState by remember{
                    mutableStateOf(recommendation!!.curtidas!!.qtdeCurtidas)
                }

                var quantidadeFavoritosState by remember{
                    mutableStateOf(recommendation!!.favoritos!!.qtdeFavoritos)
                }

                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Row() {
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = "favorita",
                            modifier = Modifier
                                .size(20.dp),
                            tint = colorResource(id = R.color.eulirio_red)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = quantidadeLikesState.toString(),
                            fontFamily = QuickSand, fontSize = 16.sp)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Bookmark,
                            contentDescription = "save",
                            modifier = Modifier
                                .size(20.dp),
                            tint = colorResource(id = R.color.eulirio_yellow_card_background)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = quantidadeFavoritosState.toString(),
                            fontFamily = QuickSand, fontSize = 16.sp)
                    }
                }
            }
        }
    ) {
        if (recommendation != null && announcement != null) RecommendationView(
            recommendation!!,
            announcement!!,
            navController,
            it.calculateBottomPadding()
        )
    }
}

@Composable
fun RecommendationView(
    recommendation: Recommendation,
    announcement: AnnouncementGet,
    navController: NavController,
    bottomBarLength: Dp
) {

    val filledStars = floor(announcement.avaliacao).toInt()
    val unfilledStars = (5 - ceil(announcement.avaliacao)).toInt()
    val halfStar = !(announcement.avaliacao.rem(1).equals(0.0))

    val mouths = listOf(
        "Jan.",
        "Fev.",
        "Mar.",
        "Abr.",
        "Mai.",
        "Jun.",
        "Jul.",
        "Ago.",
        "Set.",
        "Out.",
        "Nov.",
        "Dez."
    )
    val date = recommendation.dataHora.toString().split("T")[0].split("-")
    val mouth = mouths[(date[1].toInt() - 1)]

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp),
            backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
            shape = RoundedCornerShape(12.dp)
        ) {

            Row(
            ) {
                Image(
                    painter = rememberAsyncImagePainter(announcement.capa),
                    contentDescription = "capa do livro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(120.dp)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = announcement.titulo,
                        fontFamily = SpartanBold,
                        textAlign = TextAlign.Center
                    )
                    Row() {
                        Text(
                            text = "Escrito por ",
                            fontSize = 12.sp,
                            fontFamily = SpartanExtraLight,
                            fontWeight = FontWeight.W500
                        )

                        Text(
                            text = announcement.usuario[0].nomeUsuario,
                            fontSize = 12.sp,
                            fontFamily = SpartanMedium
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val generos = announcement.generos

                    LazyRow() {
                        items(generos) {
                            Card(
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                ,
                                backgroundColor = colorResource(R.color.eulirio_purple_text_color_border),
                                shape = RoundedCornerShape(100.dp),
                            ) {
                                Text(
                                    text = it.nome.uppercase(),
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
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp, bottom = 12.dp)
            ){

                repeat(filledStars) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }

                if (halfStar) {
                    Icon(
                        imageVector = Icons.Outlined.StarHalf,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }

                repeat(unfilledStars) {
                    Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }
            }
        }

        Column(
            Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row (
                    Modifier.clickable {
                        navController.navigate("${Routes.User.name}/${announcement.usuario[0].idUsuario}")
                    }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(announcement.usuario[0].foto),
                        contentDescription = "foto da pessoa",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(40.dp)
                            .clip(
                                CircleShape
                            ),
                        contentScale = ContentScale.Crop,
                    )

                    Column() {
                        Text(
                            text = announcement.usuario[0].nomeUsuario,
                            fontSize = 16.sp,
                            fontFamily = SpartanMedium,
                            fontWeight = FontWeight(900)
                        )
                        Text(
                            text = "@${announcement.usuario[0].userName}",
                            fontSize = 12.sp,
                            fontFamily = Spartan
                        )
                    }
                }

                Text(text = "${date[2]} $mouth ${date[0]}",
                    fontFamily = SpartanExtraLight,
                    textAlign = TextAlign.End,
                    fontSize = 14.sp)
            }

            //conteudo da resenha
            Text(text = recommendation.conteudo,
                fontFamily = QuickSand,
                fontSize = 12.sp
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview3() {
//    LoginPageTheme {
//        ShowRecommendation()
//    }
//}